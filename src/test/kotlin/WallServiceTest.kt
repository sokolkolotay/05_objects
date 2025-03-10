import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class WallServiceTest {

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun add() {
        // проверяем, что после добавления поста id стал отличным от 0

        val post = Post(
            id = 0,
            ownerId = 1,
            createdBy = 123921,
            text = "Первый пост",
            postType = "post",
            friendsOnly = false,
            markedAsAds = false,
            likes = Post.Likes(
                count = 10,
                userLikes = false,
                canLike = true,
                canPublish = true
            ),
            comments = Post.Comments(
                count = 0,
                canPost = true,
                groupsCanPost = false,
                canClose = false,
                canOpen = true
            )
        )

        val addedPost = WallService.add(post)

        assertNotEquals(0, addedPost.id)
    }

    @Test
    fun updateWithAnExistingId() {

        val post = WallService.add(
            Post(
                id = 0,
                ownerId = 2,
                createdBy = 343921,
                text = "Редактируем пост",
                postType = "post",
                friendsOnly = false,
                markedAsAds = false,
                likes = Post.Likes(
                    count = 10,
                    userLikes = false,
                    canLike = true,
                    canPublish = true
                ),
                comments = Post.Comments(
                    count = 0,
                    canPost = true,
                    groupsCanPost = false,
                    canClose = false,
                    canOpen = true
                )
            )
        )

        val updatedPost = post.copy(
            text = "Редактируем пост"
        )
        val result = WallService.update(updatedPost)

        assertEquals(true, result)
    }

    @Test
    fun updateWithAnNonExistingId() {

        val post = Post(
            id = 999,
            ownerId = 2,
            createdBy = 343921,
            text = "Редактируем пост",
            postType = "post",
            friendsOnly = false,
            markedAsAds = false,
            likes = Post.Likes(
                count = 10,
                userLikes = false,
                canLike = true,
                canPublish = true
            ),
            comments = Post.Comments(
                count = 0,
                canPost = true,
                groupsCanPost = false,
                canClose = false,
                canOpen = true
            )
        )

        val result = WallService.update(post)

        assertEquals(false, result)

    }
}