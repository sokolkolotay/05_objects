class ChatService {
    public val chats = mutableListOf<Chat>()
    public val messages = mutableListOf<Message>()
    private var lastChatId = 0
    private var lastMessageId = 0

    //Создать чат. Чат создаётся, когда пользователю отправляется первое сообщение.
    fun addChat(userId: Int): Int {
        val chat = Chat(
            id = ++lastChatId,
            chatName = "Новый чат",
            userId = userId
        )
        chats.add(chat)
        return chat.id
    }

    fun createMessage(
        userId: Int, text: String, direction: Int
    ): Int {
        val existingChat = chats.asSequence().find { it.userId == userId }

        val chatId = existingChat?.id ?: addChat(userId = userId)

        //Непрочитанными могут быть входящие сообщения
        if (direction == 1) {
            val message = Message(
                id = ++lastMessageId,
                userId = userId,
                chatId = chatId,
                text = text,
                date = "",
                viewStatus = true,
                direction = direction
            )
            messages.add(message)
            return message.id
        } else {
            val message = Message(
                id = ++lastMessageId,
                userId = userId,
                chatId = chatId,
                text = text,
                date = "",
                viewStatus = false,
                direction = direction
            )
            messages.add(message)
            return message.id
        }
    }

    fun editMessage(
        messageId: Int, text: String
    ) {
        val message = messages.asSequence().find { it.id == messageId && it.direction == 1 }
            ?: throw IllegalArgumentException("Сообщение не найдено!")
        message.text = text
    }

    fun readMessage(messageId: Int) {
        val unreadMessage =
            messages.asSequence().find { it.id == messageId && it.direction == 0 && it.viewStatus == false }

        unreadMessage?.viewStatus = true
    }

    fun deleteMessage(messageId: Int) {
        val message = messages.find { it.id == messageId } ?: throw IllegalArgumentException("Сообщение не найдено!")
        messages.removeIf { it.id == messageId }
    }

    fun deleteChat(chatId: Int) {
        val chat = chats.find { it.id == chatId } ?: throw IllegalArgumentException("Чат не найден!")
        chats.removeIf { it.id == chatId }
        messages.removeIf { it.chatId == chat.id }
    }

    //Показать сколько чатов не прочитано (хотя бы одно не прочитанное сообщение)
    fun getUnreadChatsCount(): Int {
        return messages.asSequence().filter { !it.viewStatus && it.direction == 0 }.map { it.chatId }.toSet().count()
    }

    //Получить список чатов. Первым параметром id пользователя чтобы отделять одного пользователя от другого
    fun getChats(userId: Int): List<Chat> {
        chats.find { it.userId == userId }
            ?: throw IllegalArgumentException("Чаты с указанным пользователем отсутствуют")

        val chats = chats.filter { it.userId == userId }
        return chats
    }

    fun lastMessagesFromChats(): List<String> {
        return chats.asSequence().map { chat ->
            val lastMessage = messages.asSequence()
                .filter { it.chatId == chat.id }
                .maxByOrNull { it.id }

            if (lastMessage != null) {
                "Чат с пользователем ${chat.userId}: ${lastMessage.text}"
            } else {
                "Чат с пользователем ${chat.userId}: нет сообщений"
            }
        }.toList()
    }

    //получить список сообщений из чата
    fun messagesFromChats(chatId: Int, lastMessagesId: Int, amount: Int): List<Message> {
        val resultMessages = messages.asSequence()
            .filter { it.chatId == chatId && it.id >= lastMessagesId }
            .sortedBy { it.id }
            .take(amount)
            .onEach { readMessage(it.id) }
            .toList()

        return resultMessages
    }
}