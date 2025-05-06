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

        fun of(viewer: User, messages: List<MessageV2>): MessageDetails {
            val resultOfMessage = messages.sortedBy { it.createdAt }
                .mapIndexed { index, message ->
                    MessageDetail.of(
                        viewer = viewer,
                        message = message,
                        isLatestMessage = index == messages.lastIndex
                    )
                }
                .distinct()

            return MessageDetails(messages = resultOfMessage)
        }

    }

    fun isExistsUnreadMessage(viewer: User): Boolean {
        return messages.any { it.isUnread(viewer = viewer) }
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

    fun answer(sender: User, content: MessageContent): MessageV2 {
        val toAnswerMessage = messages.last()

        return toAnswerMessage.createAnswerMessage(sender = sender, content = content)
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
        return messages.filter { it.isUnread(viewer = viewer) }
            .onEach { it.message.read(viewer = viewer) }
            .map { it.message }

    }

}
