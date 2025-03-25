// Data-класс Post (и другие классы, которые могут быть вложены в Post)
// Добавьте из перечисленных в документации около 10 полей простых типов (Int, String, Boolean).
data class Post(
    var id: Int = 1,
    val ownerId: Int = 1,
    val fromId: Int = 123456,
    val createdBy: Int = 0,
    val date: Int = 0,
    val text: String = "",
    val replyOwnerId: Int? = 0,
    val replyPostId: Int? = 0,
    val friendsOnly: Boolean = false,
    val postType: String = "",
    val signerId: Int? = 0,
    val copyHistory: List<Post>? = null,
    val canPin: Boolean = false,
    val canDelete: Boolean = false,
    val canEdit: Boolean = false,
    val isPinned: Int = 0,
    val markedAsAds: Boolean = false,
    val isFavorite: Boolean = false,
    val postponedId: Int? = 0,
    val postSource: PostSource? = null,
    val geo: Geo? = null,
    val donut: Donut? = null,
    val repost: Repost? = null,
    val views: Views? = null,
    val copyright: Copyright? = null,
    val likes: Likes? = null,
    val comments: Comments? = null,
    val attachments: List<Attachments> = emptyList()
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
    val id: Int = 1,
    val albumId: Int = 0,
    val ownerId: Int = 0,
    val userId: Int = 0,
    val text: String = "",
    val date: Int = 0,
    val photo130: String = "",
    val photo604: String = ""
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