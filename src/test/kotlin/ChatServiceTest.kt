import org.junit.Test
import org.junit.Assert.*

class ChatServiceTest {

    val chatService = ChatService()

    @Test
    fun addChat() {
        val initialCount = chatService.chats.size
        chatService.addChat(1)
        assertEquals(initialCount + 1, chatService.chats.size)
    }

    @Test
    fun createMessage() {
        val initialCount = chatService.messages.size
        chatService.createMessage(1, "Тестовое сообщение", 1)

        assertEquals(initialCount + 1, chatService.messages.size)

        chatService.createMessage(2, "Тестовое сообщение", 0)
        assertEquals(initialCount + 2, chatService.messages.size)
    }

    @Test
    fun editMessage() {
        try {
            chatService.editMessage(1, "Измененное сообщение")
        } catch (e: IllegalArgumentException) {
            assertEquals("Сообщение не найдено!", e.message)
        }

        val message = chatService.createMessage(1, "Тестовое сообщение", 1)
        chatService.editMessage(message, "Измененное сообщение")
        val result = chatService.messages.find { it.id == message }
        assertEquals("Измененное сообщение", result?.text)
    }

    @Test
    fun readMessage() {
        val message = chatService.createMessage(1, "Тестовое сообщение", 0)
        chatService.readMessage(message)
        val result = chatService.messages.find { it.id == message }
        assertTrue(result?.viewStatus == true)
    }

    @Test
    fun deleteMessage() {
        val messageId = chatService.createMessage(1, "Тестовое сообщение", 1)
        try {
            chatService.deleteMessage(0)
        } catch (e: IllegalArgumentException) {
            assertEquals("Сообщение не найдено!", e.message)
        }

        val initialCount = chatService.messages.size
        chatService.deleteMessage(messageId)

        assertEquals(initialCount - 1, chatService.messages.size)
        assertNull(chatService.messages.find { it.id == messageId })
    }

    @Test
    fun exceptionWhenDeletingAChat() {
        try {
            chatService.deleteChat(1)
        } catch (e: IllegalArgumentException) {
            assertEquals("Чат не найден!", e.message)
        }
    }

    @Test
    fun checkingChatAndMessageDeletion() {
        val messageId = chatService.createMessage(1, "Тестовое сообщение", 1)
        val chatId = chatService.chats.find { it.userId == 1 }!!.id

        val result = chatService.deleteChat(chatId)
        assertEquals(1, result)

        assertNull(chatService.chats.find { it.id == chatId })
        assertNull(chatService.messages.find { it.id == messageId })
    }

    @Test
    fun getUnreadChatsCount() {
        val message1 = chatService.createMessage(1, "Тестовое сообщение", 0)
        val message2 = chatService.createMessage(2, "Тестовое сообщение", 0)

        val secondChatId = chatService.messages.find { it.userId == 2 }!!.id
        chatService.readMessage(secondChatId)

        val expected =
            chatService.messages.filter { !it.viewStatus && it.direction == 0 }.map { it.chatId }.toSet().count()
        val actual = chatService.getUnreadChatsCount()

        assertEquals(expected, actual)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getChatsExceptionForANonExistentUser() {
        chatService.getChats(1)
    }

    @Test
    fun getChats() {
        chatService.createMessage(1, "Тестовое сообщение для user1", 0)
        chatService.createMessage(2, "Тестовое сообщение для user2", 0)

        val chatToDelete = chatService.chats.find { it.userId == 1 }!!.id
        val initialCount = chatService.messages.size

        chatService.deleteChat(chatToDelete)
        val newCount = chatService.messages.size

        val result = chatService.getChats(2)
        assertEquals(2, result[0].userId)
        assertEquals(initialCount - 1, newCount)
    }

    @Test
    fun lastMessagesFromChats() {
        //Чат 1 - два сообщения
        chatService.createMessage(1, "Первое сообщение", 0)
        chatService.createMessage(1, "Второе сообщение", 0)

        //Чат 2 - без сообщений
        chatService.addChat(2)

        val result = chatService.lastMessagesFromChats()

        assertEquals(2, result.size)
        assertTrue(result.contains("Чат с пользователем 1: Второе сообщение"))
        assertTrue(result.contains("Чат с пользователем 2: нет сообщений"))
    }

    @Test
    fun messagesFromChats() {
        val messageId = chatService.createMessage(1, "Первое сообщение", 0) // Id = 1
        chatService.createMessage(1, "Второе сообщение", 0) // Id = 2
        chatService.createMessage(1, "Третье сообщение", 0) // Id = 3

        val chatId = chatService.chats.find { it.userId == 1 }!!.id

        val result = chatService.messagesFromChats(chatId = chatId, 2, 2)

        assertEquals(2, result.size)
        assertEquals(2, result[0].id)
        assertEquals(3, result[1].id)

        assertTrue(result.all { it.viewStatus })
    }
}