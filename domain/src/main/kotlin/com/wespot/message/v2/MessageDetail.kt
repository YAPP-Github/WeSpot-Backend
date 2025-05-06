package com.wespot.message.v2

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.message.MessageContent
import com.wespot.user.User
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class MessageDetail(
    val isReceived: Boolean,
    val isSend: Boolean,
    val isAbleToAnswer: Boolean,
    val isRead: Boolean,
    val message: MessageV2
) {

    companion object {

        fun of(viewer: User, message: MessageV2, isLatestMessage: Boolean): MessageDetail {
            return MessageDetail(
                isReceived = message.isReceived(viewer = viewer),
                isSend = message.isSent(viewer = viewer),
                isAbleToAnswer = if (isLatestMessage) message.isAbleToAnswer(viewer = viewer) else false,
                isRead = message.isRead(viewer = viewer),
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

    fun createAnswerMessage(sender: User, content: MessageContent): MessageV2 {
        if (!isAbleToAnswer) {
            throw CustomException(
                message = "답장할 수 있는 쪽지가 아닙니다.",
                status = HttpStatus.BAD_REQUEST,
                view = ExceptionView.TOAST,
            )
        }

        if (message.isReceiverEver(viewer = sender)) {
            throw CustomException(
                message = "에버에게는 답장할 수 없습니다.",
                status = HttpStatus.BAD_REQUEST,
                view = ExceptionView.TOAST,
            )
        }

        return message.answerMessage(viewer = sender, content = content)
    }

}
