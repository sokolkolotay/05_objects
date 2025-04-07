data class NoteComment(
    val id: Int,
    val noteId: Int,
    val ownerId: Int,
    val authorId: Int,
    var text: String,
    val date: Int,
    var deleted: Boolean //true "Да" - false "Нет"
)