import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class WallServiceTest {

    @Before
    fun clearBeforeTest() {
        val wallService = WallService()
        wallService.clear()
    }

    @Test
    fun add() {
        // проверяем, что после добавления поста id стал отличным от 0

        val wallService = WallService()

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

        val addedPost = wallService.add(post)
        assertNotEquals(0, addedPost.id)
    }

    @Test
    fun updateWithAnExistingId() {
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

        val updatedPost = post.copy(
            text = "Редактируем пост"
        )
        val result = wallService.update(updatedPost)

        assertTrue(result)
    }

    @Test
    fun updateWithAnNonExistingId() {
        val wallService = WallService()

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
        val result = wallService.update(post)
        assertFalse(result)
    }

    // Тесты по теме "07_exceptions"
    @Test
    fun checkingAdditionOfACommentToThePost() {
        val wallService = WallService()

        val post = wallService.add(
            Post(
                id = 1,
                ownerId = 1,
                fromId = 123456,
                createdBy = 0,
                date = 0,
                text = "Тестовый пост",
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
            text = "Тестовый комментарий",
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
        //Функция отрабатывает правильно, если добавляется комментарий к правильному посту.
        assertEquals(post.id, addedComment.id)
        assertEquals("Тестовый комментарий", addedComment.text)
    }

    @Test(expected = PostNotFoundException::class)
    fun addingACommentToANonExistentPost() {
        val wallService = WallService()

        val post = wallService.add(
            Post(
                id = 1,
                ownerId = 1,
                fromId = 123456,
                createdBy = 0,
                date = 0,
                text = "Тестовый пост",
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
            text = "Тестовый комментарий",
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
        //Функция выкидывает исключение, если была попытка добавить комментарий к несуществующему посту.
        wallService.createComment(9999, comment)
    }


    //Задача№3 "Жалобы" темы "07_exceptions"
    @Test
    fun addReport() {
        val wallService = WallService()

        val post = wallService.add(
            Post(
                id = 1,
                ownerId = 1,
                fromId = 123456,
                createdBy = 0,
                date = 0,
                text = "Тестовый пост",
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
            text = "Тестовый комментарий",
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
        wallService.createComment(post.id, comment)

        wallService.reportComment(1, 0)
        assertTrue(
            "Жалоба на комментарий с ID ${comment.id} должна быть успешно добавлена",
            wallService.getReports().any { it.commentId == 1 && it.reason == ReportReason.SPAM }
        )
    }

}