class NoteService {
    public val notes = mutableListOf<Note>()
    public val comments = mutableListOf<NoteComment>()
    private var lastId = 0
    private var lastCommentId = 0

    fun add(
        ownerId: Int = 1,
        title: String,
        text: String,
        privacy: Int = 1,
        commentPrivacy: Int = 1,
        privacyView: String = "",
        privacyComment: String = ""
    ): Int {
        val note = Note(
            id = ++lastId,
            ownerId = ownerId,
            title = title,
            text = text,
            date = 0,
            comments = 0,
            readComments = 0,
            viewUrl = "",
            deleted = false,
            privacy = 1,
            commentPrivacy = 1,
            privacyView = "",
            privacyComment = ""
        )
        notes.add(note)
        return note.id
    }

    fun createComment(noteId: Int, message: String): Int {
        val note = notes.find { it.id == noteId } ?: throw IllegalArgumentException("Заметка не найдена!")
        val comment = NoteComment(
            id = ++lastCommentId, noteId = noteId, text = message, date = 0, deleted = false, ownerId = 1, authorId = 1
        )
        comments.add(comment)
        return comment.id
    }

    fun delete(noteId: Int): Int {
        val note = notes.find { it.id == noteId } ?: throw IllegalArgumentException("Заметка не найдена!")
        val removedNote = notes.removeIf { it.id == noteId }
        //проверяем были ли добавлены комментарии к заметке. Если да, то переносим их в удаленные(фактически их не удаляем)
        comments.filter { it.noteId == noteId && !it.deleted }.forEach { it.deleted = true }

        return 1
    }

    fun deleteComment(commentId: Int): Int {
        val comment = comments.find { it.id == commentId } ?: throw IllegalArgumentException("Комментарий не найден!")
        val deletedComment =
            comments.find { it.deleted == false } ?: throw IllegalStateException("Комментарий удален ранее!")

        comment.deleted = true
        return 1
    }

    fun edit(
        noteId: Int,
        title: String,
        text: String,
        privacy: Int,
        commentPrivacy: Int,
        privacyView: String,
        privacyComment: String
    ): Int {
        val note = notes.find { it.id == noteId } ?: throw IllegalArgumentException("Заметка не найдена!")

        note.title = title
        note.text = text
        note.privacy = privacy
        note.commentPrivacy = commentPrivacy
        note.privacyView = privacyView
        note.privacyComment = privacyComment

        return 1
    }

    fun editComment(commentId: Int, message: String): Int {
        val comment = comments.find { it.id == commentId } ?: throw IllegalArgumentException("Комментарий не найден!")
        val deletedComment =
            comments.find { it.deleted == false } ?: throw IllegalStateException("Комментарий удален ранее!")

        comment.text = message

        return 1
    }

    fun get(noteIds: List<Int>, userId: Int, offset: Int = 0, count: Int, sort: Int): List<Note> {
        if (userId < 0 || offset < 0 || count < 0 || sort < 0) throw IllegalArgumentException("Неверные параметры запроса")

        var filteredNotes = notes.filter { userId == it.ownerId }

        if (!noteIds.isNullOrEmpty()) {
            filteredNotes = filteredNotes.filter { userId == it.ownerId }
        }

        filteredNotes = when (sort) {
            0 -> filteredNotes.sortedByDescending { it.date }
            1 -> filteredNotes.sortedBy { it.date }
            else -> filteredNotes
        }

        return filteredNotes.drop(offset).take(count)
    }

    fun getById(noteId: Int, ownerId: Int): Note {
        if (noteId < 0 || ownerId < 0) throw IllegalArgumentException("Неверные параметры запроса")
        val note = notes.find { it.id == noteId && it.ownerId == ownerId }
            ?: throw IllegalArgumentException("Заметка не найдена!")

        return note.copy(privacy = note.privacy, commentPrivacy = note.commentPrivacy)
    }

    fun getComments(noteId: Int, ownerId: Int, sort: Int, offset: Int = 10, count: Int): List<Map<String, Any?>> {
        if (noteId < 0 || ownerId < 0 || sort < 0 || offset < 0 || count < 0) throw IllegalArgumentException("Неверные параметры запроса")
        val note = notes.find { it.id == noteId && it.ownerId == ownerId }
            ?: throw IllegalArgumentException("Заметка не найдена")
        comments.find { it.noteId == noteId && !it.deleted } ?: throw IllegalStateException("Комментарии отсутствуют")

        var filteredComments = comments.filter { it.id == noteId && it.ownerId == ownerId && !it.deleted }

        filteredComments = when (sort) {
            0 -> filteredComments.sortedBy { it.date }
            1 -> filteredComments.sortedByDescending { it.date }
            else -> filteredComments
        }

        return filteredComments.drop(offset).take(count).map {
            mapOf(
                "id" to it.id,
                "uid" to it.authorId,
                "nid" to it.noteId,
                "oid" to it.ownerId,
                "date" to it.date,
                "message" to it.text
            )
        }
    }

    fun restoreComment(commentId: Int, ownerId: Int): Int {
        val comment = comments.find { it.id == commentId && it.ownerId == ownerId && it.deleted }
            ?: throw IllegalArgumentException("Комментарий отсутствует")

        comment.deleted = false
        return 1
    }
}