package com.wespot.message.v2

import com.wespot.user.User
import java.time.LocalDateTime

data class MessageDetails(
    val messages: List<MessageDetail>
) {

    companion object {

        fun of(viewer: User, messages: List<MessageV2>): MessageDetails {
            val resultOfMessage = messages.sortedBy { it.createdAt }
                .map { MessageDetail.of(viewer = viewer, message = it) }

            return MessageDetails(messages = resultOfMessage)
        }

    }

    fun isExistsUnreadMessage(viewer: User): Boolean {
        return messages.any { it.isUnread(viewer = viewer) }
    }

    fun chatsTime(): List<LocalDateTime> {
        return messages.map { it.chatTime() }
    }

    fun asList(): List<MessageDetail> {
        return messages
    }

}
