package com.wespot.message.v2

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.message.MessageContent
import com.wespot.user.User
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class MessageDetails(
    val messages: List<MessageDetail>
) {

    companion object {

        fun of(viewer: User, alreadyUsedMessageOnToday: Int, messages: List<MessageV2>): MessageDetails {
            val resultOfMessage = messages.sortedBy { it.createdAt }
                .mapIndexed { index, message ->
                    MessageDetail.of(
                        viewer = viewer,
                        message = message,
                        alreadyUsedMessageOnToday = alreadyUsedMessageOnToday,
                        isLatestMessage = index == messages.lastIndex,
                        roomMessage = messages.first { it.isRoom() }
                    )
                }
                .distinct()

            return MessageDetails(messages = resultOfMessage)
        }

    }

    fun isExistsUnreadMessage(): Boolean {
        return messages.any { !it.isRead }
    }

    fun chatsTime(): List<LocalDateTime> {
        return messages.map { it.chatTime() }
    }

    fun asList(viewer: User): List<MessageDetail> {
        return asExcludeDeleteMessageDetails(viewer = viewer)
    }

    fun isAbleToAnswer(): Boolean {
        return messages.any { it.isAbleToAnswer }
    }

    fun answer(sender: User, alreadyUsedMessageOnToday: Int, content: MessageContent): MessageV2 {
        val toAnswerMessage = messages.last()

        return toAnswerMessage.createAnswerMessage(
            sender = sender,
            alreadyUsedMessageOnToday = alreadyUsedMessageOnToday,
            content = content,
            roomMessage = messages.first().message
        )
    }

    fun deleteMessage(viewer: User, messageId: Long): MessageV2 {
        val toDeleteMessage = messages.find { it.message.id == messageId } ?: throw CustomException(
            message = "삭제하려는 쪽지를 찾을 수 없습니다.",
            status = HttpStatus.BAD_REQUEST,
            view = ExceptionView.TOAST,
        )

        return toDeleteMessage.delete(deleter = viewer)
    }

    private fun asExcludeDeleteMessageDetails(viewer: User): List<MessageDetail> {
        return messages.filter { it.isNotDeleted(viewer = viewer) }
    }

    fun readUnreadMessage(viewer: User): List<MessageV2> {
        return messages.filter { it.isUnread() }
            .onEach { it.message.read(viewer = viewer) }
            .map { it.message }
    }

}
