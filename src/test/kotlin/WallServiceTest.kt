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


        val addedPost = WallService.add(post)

        assertNotEquals(0, addedPost.id)
    }

    @Test
    fun updateWithAnExistingId() {

        val post = WallService.add(
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

        val updatedPost = post.copy(
            text = "Редактируем пост"
        )
        val result = WallService.update(updatedPost)

        assertTrue(result)
    }

    @Test
    fun updateWithAnNonExistingId() {

        val post = Post(
            id = 9999,
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

        val result = WallService.update(post)

        assertFalse(result)

    }
}