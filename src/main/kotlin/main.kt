// Data-класс Post (и другие классы, которые могут быть вложены в Post)
// Добавьте из перечисленных в документации около 10 полей простых типов (Int, String, Boolean).
data class Post(
    var id: Int,
    val ownerId: Int,
    val fromId: Int,
    val createdBy: Int,
    val date: Int,
    val text: String,
    val replyOwnerId: Int?,
    val replyPostId: Int?,
    val friendsOnly: Boolean,
    val postType: String,
    val signerId: Int?,
    val copyHistory: List<Post>?,
    val canPin: Boolean,
    val canDelete: Boolean,
    val canEdit: Boolean,
    val isPinned: Int,
    val markedAsAds: Boolean,
    val isFavorite: Boolean,
    val postponedId: Int?,
    val postSource: PostSource?,
    val geo: Geo?,
    val donut: Donut?,
    val repost: Repost?,
    val views: Views?,
    val copyright: Copyright?,
    val likes: Likes?,
    val comments: Comments?,
    val attachments: List<Attachments>
)

data class PostSource(
    val type: String,
    val platform: String,
    val data: String,
    val url: String
)

data class Geo(
    val type: String,
    val coordinates: String,
    val place: Place
) {
    data class Place(
        val id: Int,
        val title: String,
        val latitude: Int,
        val longitude: Int,
        val created: Int,
        val icon: String,
        val checkins: Int,
        val updated: Int,
        val type: Int,
        val city: Int,
        val address: String
    )
}

data class Donut(
    val isDonut: Boolean,
    val paidDuration: Int,
    val canPublishFreeCopy: Boolean,
    val editMode: String,
    val placeHolder: PlaceHolder? = null
) {
    class PlaceHolder()
}

data class Repost(
    val count: Int,
    val userReposted: Boolean
)

data class Views(
    val count: Int
)

data class Copyright(
    val id: Int,
    val link: String,
    val name: String,
    val type: String
)

data class Likes(
    val count: Int,
    val userLikes: Boolean,
    val canLike: Boolean,
    val canPublish: Boolean
)

data class Comments(
    val count: Int,
    val canPost: Boolean,
    val groupsCanPost: Boolean,
    val canClose: Boolean,
    val canOpen: Boolean
)

sealed class Attachments {
    abstract val type: String
}

data class PhotoAttachments(val photo: Photo) : Attachments() {
    override val type = "photo"
}

data class VideoAttachments(val video: Video) : Attachments() {
    override val type = "video"
}

data class AudioAttachments(val audio: Audio) : Attachments() {
    override val type = "audio"
}

data class FileAttachments(val file: File) : Attachments() {
    override val type = "file"
}

data class LinkAttachments(val link: Link) : Attachments() {
    override val type = "link"
}

data class Photo(
    val id: Int,
    val albumId: Int,
    val ownerId: Int,
    val userId: Int,
    val text: String,
    val date: Int,
    val photo130: String,
    val photo604: String
)

data class Video(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val description: Int,
    val duration: Int,
    val date: Int
)

data class Audio(
    val id: Int,
    val ownerId: Int,
    val artist: String,
    val title: String,
    val duration: Int,
    val url: String,
    val lyricsId: Int,
    val albumId: Int
)

data class File(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val size: Int,
    val ext: String,
    val url: String,
    val date: Int,
    val type: Int
)

data class Link(
    val url: String,
    val title: String,
    val caption: String,
    val description: String,
    val previewPage: String,
    val previewUrl: String
)

// Объект WallService, который хранит посты в массиве.
object WallService {
    private var posts = emptyArray<Post>()
    private var newId: Int = 0

    fun clear() {
        posts = emptyArray()
        // также здесь нужно сбросить счетчик для id постов, если он у вас используется
        newId = 0
    }

    fun add(post: Post): Post {

        // добавлять запись в массив, но при этом назначать посту уникальный среди всех постов идентификатор;
        // возвращать пост с уже выставленным идентификатором.

        val newPost = post.copy(id = ++newId)
        posts += newPost
        return newPost
    }

    fun update(post: Post): Boolean {

        // находить среди всех постов запись с тем же id, что и у post и обновлять все свойства;
        // если пост с таким id не найден, то ничего не происходит и возвращается false, иначе – возвращается true.

        for (index in posts.indices) {
            if (posts[index].id == post.id) {
                posts[index] = post.copy(id = posts[index].id)
                return true
            }
        }
        return false
    }

}

fun main() {

}

