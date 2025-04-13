data class Message(
    val id: Int,
    var chatId: Int,
    val userId: Int,
    var text: String,
    val date: String = "",
    var viewStatus: Boolean = false, // false -> не просмотренное, true -> просмотренное
    val direction: Int = 1 // 0 -> входящее, 1 -> исходящее
)