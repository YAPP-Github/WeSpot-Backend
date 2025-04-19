package com.wespot.message

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.User
import com.wespot.user.message.AnonymousProfile
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class MessageV2(
    val id: Long,
    val content: MessageContent,
    val sender: User,
    val receiver: User,
    val isReceiverRead: Boolean,
    val readAt: LocalDateTime?,
    val isReported: Boolean,

    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,

    val isSenderDeleted: Boolean,
    val senderDeletedAt: LocalDateTime?,

    val isReceiverDeleted: Boolean,
    val receiverDeletedAt: LocalDateTime?,

    val messageRoomId: Long?,
    val messageRoomOwnerId: Long,
    val isBookmarked: Boolean,

    val anonymousProfile: AnonymousProfile?,
) {

    companion object {

        private const val COUNT_OF_MAX_ABLE_TO_SEND_MESSAGE_PER_DAY = 3

        fun createInitial(
            content: String,
            sender: User,
            receiver: User,
            anonymousProfile: AnonymousProfile?,
            alreadyUsedMessageOnToday: Int,
            isBlockedFromReceiver: Boolean
        ): MessageV2 {
            if (COUNT_OF_MAX_ABLE_TO_SEND_MESSAGE_PER_DAY <= alreadyUsedMessageOnToday) {
                throw CustomException(
                    HttpStatus.BAD_REQUEST,
                    ExceptionView.TOAST,
                    "하루에 쪽지는 3개만 보낼 수 있습니다."
                )
            }

            if (isBlockedFromReceiver) {
                throw CustomException(
                    HttpStatus.FORBIDDEN,
                    ExceptionView.TOAST,
                    "차단당한 상대에게는 쪽지를 보낼 수 없습니다."
                )
            }

            return MessageV2(
                id = 0,
                content = MessageContent.from(content),
                sender = sender,
                receiver = receiver,
                isReceiverRead = false,
                readAt = null,
                isReported = false,

                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),

                isSenderDeleted = false,
                senderDeletedAt = null,

                isReceiverDeleted = false,
                receiverDeletedAt = null,

                messageRoomId = null,
                messageRoomOwnerId = sender.id,
                isBookmarked = false,

                anonymousProfile = anonymousProfile
            )
        }

    }

    fun isRoom(): Boolean {
        return messageRoomId == null
    }

}
