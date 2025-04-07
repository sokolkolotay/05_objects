import org.junit.Test
import org.junit.Assert.*
import org.junit.rules.ExpectedException

class NoteServiceTest {

    @Test
    fun add() {
        val noteService = NoteService()

        val createdNote = noteService.add(1, "Первая заметка", "Содержание заметки")
        assertNotEquals(0, 1)
    }

    @Test
    fun createComment() {
        val noteService = NoteService()
        val createNote = noteService.add(1, "Тестовая заметка", "Содержание заметки")

        val commentNote1 = noteService.createComment(1, "Тестовый комментарий")
        assertNotEquals(0, 1)
    }

    @Test
    fun creatingCommentWithoutANote() {
        val noteService = NoteService()
        try {
            val commentNote1 = noteService.createComment(1, "Тестовый комментарий")
        } catch (e: IllegalArgumentException) {
            assertEquals("Заметка не найдена!", e.message)
        }
    }

    @Test
    fun deletingANonExistentNote() {
        val noteService = NoteService()

        val exception = assertThrows(IllegalArgumentException::class.java) {
            noteService.delete(1)
        }
    }

    @Test
    fun theNoteWasDeletedButThereIsNoComment() {
        val noteService = NoteService()
        val createdNote = noteService.add(1, "Тестовая заметка", "Содержание заметки")
        noteService.createComment(createdNote, "Содержание комментария")
        noteService.delete(createdNote)

        val deleted = noteService.comments.filter { it.noteId == createdNote }
        assertTrue(deleted.all { it.deleted })
    }

    @Test
    fun deleteComment() {
        val commentId = 1

        val noteService = NoteService()
        val createdNote = noteService.add(1, "Тестовая заметка", "Содержание заметки")
        val createdComment = noteService.createComment(1, "Содержание комментария")
        noteService.deleteComment(1)

        val deleted = noteService.comments.filter { it.id == commentId }
        assertTrue(deleted.all { it.deleted })
    }

    @Test
    fun checkingForExceptionsWhenEditingANote() {
        val noteService = NoteService()
        val createdNote = noteService.add(1, "Тестовая заметка", "Текст заметки")
        try {
            noteService.edit(0, "Редактирование заметки", "Текст отредактированной заметки", 1, 1, "", "")
        } catch (e: IllegalArgumentException) {
            assertEquals("Заметка не найдена!", e.message)
        }
    }

    @Test
    fun checkingForChangesWhenEditingANote() {
        val noteService = NoteService()
        val createdNote = noteService.add(1, "Тестовая заметка", "Текст заметки")
        val changeResult = noteService.edit(createdNote, "Новый заголовок", "Новый текст", 2, 2, "", "")

        val result = noteService.notes.find { it.id == createdNote }!!

        assertEquals("Новый заголовок", result.title)
        assertEquals("Новый текст", result.text)
        assertEquals(2, result.privacy)
        assertEquals(2, result.commentPrivacy)
        assertEquals("", result.privacyView)
        assertEquals("", result.privacyComment)
    }

    @Test
    fun checkingForExceptionsWhenEditingANonExistentComment() {
        val noteService = NoteService()
        val createdNote = noteService.add(1, "Тестовая заметка", "Текст заметки")
        val createdComment = noteService.createComment(1, "Текст комментария")

        try {
            noteService.editComment(0, "Новый текст комментария")
        } catch (e: IllegalArgumentException) {
            assertEquals("Комментарий не найден!", e.message)
        }
    }

    @Test
    fun checkingForExceptionsWhenEditingADeletedComment() {
        val noteService = NoteService()
        val createdNote = noteService.add(1, "Тестовая заметка", "Текст заметки")
        val createdComment = noteService.createComment(1, "Текст комментария")
        noteService.deleteComment(1)

        try {
            noteService.editComment(1, "Новый текст комментария")
        } catch (e: IllegalStateException) {
            assertEquals("Комментарий удален ранее!", e.message)
        }
    }

    @Test
    fun editingComment() {
        val noteService = NoteService()
        val createdNote = noteService.add(1, "Тестовая заметка", "Текст заметки")
        val createdComment = noteService.createComment(1, "Текст комментария")
        noteService.editComment(1, "Новый текст комментария")

        val result = noteService.comments.find { it.id == 1 }!!

        assertEquals("Новый текст комментария", result.text)
    }

    @Test(expected = IllegalArgumentException::class)
    fun checkingTheExceptionForGet() {
        val noteService = NoteService()
        noteService.get(listOf(-3), -1, -1, -1, -1)
    }

    @Test
    fun getFiltersNotesByIdIfProvided() {
        val noteService = NoteService()
        val note1 = noteService.add(1, "Первая заметка", "Текст заметки")
        val note2 = noteService.add(2, "Вторая заметка", "Текст заметки")

        val getNotes1 = noteService.get(noteIds = listOf(note1), userId = 1, count = 10, sort = 0)
        val getNotes2 = noteService.get(noteIds = listOf(note2), userId = 1, count = 10, sort = 1)
        val getNotes3 = noteService.get(noteIds = listOf(note1), userId = 1, count = 10, sort = 3)

        assertEquals(1, getNotes1.size)
        assertEquals(note1, getNotes1[0].id)
        assertEquals(1, getNotes2.size)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getByIdCheckingTheCorrectnessOfAnException() {
        val noteService = NoteService()

        noteService.getById(-1, -1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getByIdCheckingTheExceptionForValidIds() {
        val noteService = NoteService()
        val createdNote = noteService.add(1, "Первая заметка", "Текст заметки")

        noteService.getById(2, 1)
    }

    @Test
    fun getById() {
        val noteService = NoteService()
        val createdNote = noteService.add(1, "Первая заметка", "Текст заметки")
        val result = noteService.getById(1, 1)

        assertEquals(1, result.privacy)
        assertEquals(1, result.commentPrivacy)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getCommentCheckingTheExceptionForInvalidParameters() {
        val noteService = NoteService()
        noteService.getComments(-1, -1, -1, -1, -1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getCommentCheckingAnExceptionForAMissingId() {
        val noteService = NoteService()

        //попытаемся получить комментарии у несуществующей заметки
        noteService.getComments(1, 1, 1, 1, 1)
    }

    @Test(expected = IllegalStateException::class)
    fun getCommentCheckingAnExceptionForAMissingComment() {
        val noteService = NoteService()
        val note = noteService.add(1, "Тестовая заметка", "Текст заметки")
        val comment = noteService.createComment(1, "Тестовый комментарий")
        noteService.deleteComment(1)

        noteService.getComments(1, 1, 1, 1, 1)
    }

    @Test
    fun checkingCommentFiltersAndResult() {
        val noteService = NoteService()
        val note1 = noteService.add(1, "Первая заметка", "Текст заметки")
        val note2 = noteService.add(2, "Вторая заметка", "Текст заметки")

        noteService.createComment(1, "Первый комментарий")
        noteService.createComment(2, "Второй комментарий")

        val result = noteService.getComments(1, 1, 0, 0, 10)
        val result2 = noteService.getComments(1, 1, 1, 0, 10)
        val result3 = noteService.getComments(1, 1, 3, 0, 10)

        assertEquals(1, result.size)
        assertEquals("Первый комментарий", result[0]["message"])
        assertEquals("Первый комментарий", result2[0]["message"])
        assertEquals("Первый комментарий", result3[0]["message"])
    }

    @Test(expected = IllegalArgumentException::class)
    fun restoreCommentCheckingAnExceptionForAMissingComment() {
        val noteService = NoteService()

        noteService.restoreComment(1, 1)
    }

    @Test
    fun restoreComment() {
        val noteService = NoteService()
        val note = noteService.add(1, "Первая заметка", "Текст заметки")
        val comment = noteService.createComment(1, "Первый комментарий")
        //удалим комментарий
        noteService.deleteComment(1)
        //восстановим удаленный комментарий
        val result = noteService.restoreComment(1, 1)

        assertEquals(1, result)
    }
}
