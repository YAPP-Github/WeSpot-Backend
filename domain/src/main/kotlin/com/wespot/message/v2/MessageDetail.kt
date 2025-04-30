package com.wespot.message.v2

import com.wespot.user.User
import java.time.LocalDateTime

data class MessageDetail(
    val isReceived: Boolean,
    val isSend: Boolean,
    val message: MessageV2
) {

    companion object {

        fun of(viewer: User, message: MessageV2): MessageDetail {
            return MessageDetail(
                isReceived = message.isReceived(viewer = viewer),
                isSend = message.isSent(viewer = viewer),
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
