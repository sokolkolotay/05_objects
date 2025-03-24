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

//Задача№3 "Жалобы" темы "07_exception"

enum class ReportReason(val reportId: Int) {
    SPAM(0),
    CHILD_PORNOGRAPHY(1),
    EXTREMISM(2),
    VIOLENCE(3),
    DRUG_PROPAGANDA(4),
    ADULT_CONTENT(5),
    ABUSE(6),
    SUICIDE_CALLS(8);
}

data class Report(
    val commentId: Int,
    val reason: ReportReason
)

// Объект WallService, который хранит посты в массиве.
class WallService {
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var reports = emptyArray<Report>()
    private var newId: Int = 0

    fun createComment(postId: Int, comment: Comment): Comment {
        for (post in posts) {
            if (post.id == postId) {
                comments += comment
                return comments.last()
            }
        }
        throw PostNotFoundException("Пост с ID: $postId не найден")
    }

    fun clear() {
        posts = emptyArray()
        // также здесь нужно сбросить счетчик для id постов, если он у вас используется
        comments = emptyArray()
        reports = emptyArray()
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

    fun getReports(): Array<Report> {
        return reports
    }

    fun reportComment (commentId: Int, reasonId: Int) {
        val reason = ReportReason.values().find { it.reportId == reasonId }
            ?: throw IllegalArgumentException("Неверная причина жалобы: $reasonId")

        val comment = comments.find { it.id == commentId }
            ?: throw CommentNotFoundException("Комментарий с ID: $commentId не найден")

        val post = posts.find { it.id == comment.id }
            ?: throw PostNotFoundException("Пост для комментария с ID: $commentId не найден")

        reports += Report(commentId, reason)
        println("Жалоба на комментарий с ID: $commentId по причине: $reason успешно добавлена")
    }

}

class CommentNotFoundException(message: String): RuntimeException(message)
class PostNotFoundException(message: String) : RuntimeException(message)

data class Comment(
    val id: Int,
    val fromId: Int,
    val date: Int,
    val text: String,
    val donut: Donut,
    val replyToUser: Int,
    val replyToComment: Int,
    val attachments: Attachments,
    val parentsStack: List<Comment>,
    val thread: Thread
) {
    data class Donut(
        val isDon: Boolean,
        val placeholder: String
    )

    data class Thread(
        val count: Int,
        val items: List<Thread>,
        val canPost: Boolean,
        val showReplyButton: Boolean,
        val groupsCanPost: Boolean
    )
}

fun main() {

    val wallService = WallService()

    val post = wallService.add(
        Post(
            id = 1,
            ownerId = 1,
            fromId = 123456,
            createdBy = 0,
            date = 0,
            text = "_",
            replyOwnerId = 0,
            replyPostId = 0,
            friendsOnly = false,
            postType = "_",
            signerId = 0,
            copyHistory = null,
            canPin = false,
            canDelete = false,
            canEdit = false,
            isPinned = 0,
            markedAsAds = false,
            isFavorite = false,
            postponedId = 0,
            postSource = null,
            geo = null,
            donut = null,
            repost = null,
            views = null,
            copyright = null,
            likes = null,
            comments = null,
            attachments = emptyList()
        )
    )

    val comment = Comment(
        id = 1,
        fromId = 1,
        date = 0,
        text = "",
        donut = Comment.Donut(
            isDon = false,
            placeholder = ""
        ),
        replyToUser = 0,
        replyToComment = 0,
        attachments = PhotoAttachments(
            photo = Photo(
                id = 0,
                albumId = 0,
                ownerId = 0,
                userId = 0,
                text = "",
                date = 0,
                photo130 = "",
                photo604 = ""
            )
        ),
        parentsStack = emptyList(),
        thread = Comment.Thread(
            count = 0,
            items = emptyList(),
            canPost = false,
            showReplyButton = false,
            groupsCanPost = false
        )
    )

    val addedComment = wallService.createComment(post.id, comment)
    println("Комментарий успешно добавлен: $addedComment")
}

