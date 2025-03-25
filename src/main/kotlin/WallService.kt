// Объект WallService, который хранит посты в массиве.
class WallService {
    private var posts = emptyArray<Post>()
    var comments = emptyArray<Comment>()
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
