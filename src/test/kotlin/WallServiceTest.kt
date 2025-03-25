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
        val post = Post()

        val addedPost = wallService.add(post)
        assertNotEquals(0, addedPost.id)
    }

    @Test
    fun updateWithAnExistingId() {
        val wallService = WallService()
        val post = wallService.add(Post())

        val updatedPost = post.copy(text = "Редактируем пост")
        val result = wallService.update(updatedPost)
        assertTrue(result)
    }

    @Test
    fun updateWithAnNonExistingId() {
        val wallService = WallService()
        val post = Post()
        val result = wallService.update(post)
        assertFalse(result)
    }

    // Тесты по теме "07_exceptions"
    @Test
    fun checkingAdditionOfACommentToThePost() {
        val wallService = WallService()
        val post = wallService.add(Post())
        val comment = Comment(text = "Тестовый комментарий")

        val addedComment = wallService.createComment(post.id, comment)
        //Функция отрабатывает правильно, если добавляется комментарий к правильному посту.
        assertEquals(post.id, addedComment.id)
        assertEquals("Тестовый комментарий", addedComment.text)
    }

    @Test(expected = PostNotFoundException::class)
    fun addingACommentToANonExistentPost() {
        val wallService = WallService()
        val post = wallService.add(Post())

        val comment = Comment(text = "Тестовый комментарий")
        //Функция выкидывает исключение, если была попытка добавить комментарий к несуществующему посту.
        wallService.createComment(9999, comment)
    }

    //Задача №3 "Жалобы" темы "07_exceptions"

    //проверяем на существующий сommentID
    @Test(expected = CommentNotFoundException::class)
    fun reportNonExistentComment() {
        val wallService = WallService()
        val post = wallService.add(Post())

        wallService.reportComment(999, ReportReason.SPAM.reportId)
    }

    //проверяем что указана верная причина жалобы, но нет поста
    @Test(expected = PostNotFoundException::class)
    fun reportCommentWithValidReasonButMissingPost() {
        val wallService = WallService()
        val comment = Comment(id = 1, text = "Тестовый комментарий")

        // Добавляем комментарий напрямую без поста
        wallService.comments += comment

        // Правильный reasonId, но нет поста для этого комментария
        wallService.reportComment(comment.id, ReportReason.SPAM.reportId)
    }

    //проверяем исключение на указание несуществующей жалобы
    @Test
    fun reportCommentWithInvalidReason() {
        val wallService = WallService()
        val post = wallService.add(Post())
        val comment = wallService.createComment(post.id, Comment(text = "Тестовый комментарий"))

        val invalidReasonId = 999

        val exception = assertThrows(IllegalArgumentException::class.java) {
            wallService.reportComment(comment.id, invalidReasonId)
        }

        assertEquals("Неверная причина жалобы: $invalidReasonId", exception.message)
    }

    //проверяем успешное добавление жалобы при соблюдении всех условий
    @Test
    fun addReport() {
        val wallService = WallService()
        val post = wallService.add(Post())
        val comment = Comment(text = "Тестовый комментарий")

        wallService.createComment(post.id, comment)

        wallService.reportComment(1, 0)
        assertTrue(
            "Жалоба на комментарий с ID ${comment.id} должна быть успешно добавлена",
            wallService.getReports().any { it.commentId == 1 && it.reason == ReportReason.SPAM })
    }
}