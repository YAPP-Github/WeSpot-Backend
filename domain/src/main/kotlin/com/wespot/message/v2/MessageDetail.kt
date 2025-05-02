package com.wespot.message.v2

import com.wespot.user.User
import java.time.LocalDateTime

data class MessageDetail(
    val isReceived: Boolean,
    val isSend: Boolean,
    val isAbleToAnswer: Boolean,
    val message: MessageV2
) {

    companion object {

        fun of(viewer: User, message: MessageV2, isLatestMessage: Boolean): MessageDetail {
            return MessageDetail(
                isReceived = message.isReceived(viewer = viewer),
                isSend = message.isSent(viewer = viewer),
                isAbleToAnswer = if (isLatestMessage) message.isAbleToAnswer(viewer = viewer) else false,
                message = message
            )
        }

    }

    fun isUnread(viewer: User): Boolean {
        return !message.isRead(viewer = viewer)
    }

    fun chatTime(): LocalDateTime {
        return message.createdAt
    }

}
