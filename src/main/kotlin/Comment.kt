data class Comment(
    val id: Int = 1,
    val fromId: Int = 1,
    val date: Int = 0,
    val text: String = "",
    val donut: Donut = Comment.Donut(),
    val replyToUser: Int = 0,
    val replyToComment: Int = 0,
    val attachments: Attachments = PhotoAttachments(photo = Photo()),
    val parentsStack: List<Comment> = emptyList(),
    val thread: Thread = Comment.Thread()
) {
    data class Donut(
        val isDon: Boolean = false,
        val placeholder: String = ""
    )

    data class Thread(
        val count: Int = 0,
        val items: List<Thread> = emptyList(),
        val canPost: Boolean = false,
        val showReplyButton: Boolean = false,
        val groupsCanPost: Boolean = false
    )
}