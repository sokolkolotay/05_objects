// Data-класс Post (и другие классы, которые могут быть вложены в Post)
// Добавьте из перечисленных в документации около 10 полей простых типов (Int, String, Boolean).
data class Post(
    var id: Int,
    val ownerId: Int,
    val createdBy: Int,
    val text: String,
    val postType: String,
    val friendsOnly: Boolean,
    val markedAsAds: Boolean,
    val likes: Likes,
    val comments: Comments
) {
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
}

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

