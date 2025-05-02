package com.wespot.message.v2

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.message.MessageContent
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
    val isBlocked: Boolean,
    val isReported: Boolean,

    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,

    val isSenderDeleted: Boolean,
    val senderDeletedAt: LocalDateTime?,

    val isReceiverDeleted: Boolean,
    val receiverDeletedAt: LocalDateTime?,

    val messageRoomId: Long?,
    val messageRoomOwnerId: Long,
    val isSenderBookmarked: Boolean,
    val isReceiverBookmarked: Boolean,

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
//            isBlockedFromReceiver: Boolean // 정책 논의중
        ): MessageV2 {
            if (COUNT_OF_MAX_ABLE_TO_SEND_MESSAGE_PER_DAY <= alreadyUsedMessageOnToday) {
                throw CustomException(
                    HttpStatus.BAD_REQUEST,
                    ExceptionView.TOAST,
                    "하루에 쪽지는 3개만 보낼 수 있습니다."
                )
            }

//            if (isBlockedFromReceiver) {
//                throw CustomException(
//                    HttpStatus.FORBIDDEN,
//                    ExceptionView.TOAST,
//                    "차단당한 상대에게는 쪽지를 보낼 수 없습니다."
//                )
//            }

            return MessageV2(
                id = 0,
                content = MessageContent.from(content),
                sender = sender,
                receiver = receiver,
                isReceiverRead = false,
                readAt = null,

                isBlocked = false,
                isReported = false,

                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),

                isSenderDeleted = false,
                senderDeletedAt = null,

                isReceiverDeleted = false,
                receiverDeletedAt = null,

                messageRoomId = null,
                messageRoomOwnerId = sender.id,
                isSenderBookmarked = false,
                isReceiverBookmarked = false,

                anonymousProfile = anonymousProfile
            )
        }

    }

    fun isRoom(): Boolean {
        return messageRoomId == null
    }

    private fun validateRoomMessage() {
        if (!isRoom()) {
            throw CustomException(
                message = "쪽지 방이 아닙니다.",
                status = HttpStatus.BAD_REQUEST,
                view = ExceptionView.TOAST,
            )
        }
    }

    private fun validateMessageDetail() {
        if (isRoom()) {
            throw CustomException(
                message = "쪽지 방입니다.",
                status = HttpStatus.BAD_REQUEST,
                view = ExceptionView.TOAST,
            )
        }
    }

    fun isReceived(viewer: User): Boolean {
        return viewer.id == receiver.id
    }

    fun isSent(viewer: User): Boolean {
        return viewer.id == sender.id
    }

    fun isContainsOf(roomMessage: MessageV2): Boolean {
        validateMessageDetail()
        return roomMessage.id == this.messageRoomId
    }

    fun isRead(viewer: User): Boolean {
        if (viewer.isMeSender(senderId = sender.id)) {
            return true
        }

        return readAt != null
    }

    fun isMeOwnerOfMessageRoom(viewer: User): Boolean {
        validateRoomMessage()

        return messageRoomOwnerId == viewer.id
    }

    fun receiverName(viewer: User): String {
        validateRoomMessage()

        if (viewer.isMeSender(senderId = sender.id)) {
            return receiver.name
        }

        if (receiverUsingAnonymousProfile(viewer = viewer)) {
            return anonymousProfile!!.name
        }

        return sender.name
    }

    private fun receiverUsingAnonymousProfile(viewer: User): Boolean {
        validateRoomMessage()

        if (viewer.isMeSender(senderId = sender.id)) {
            return false
        }

        return anonymousProfile != null
    }

    fun isAnonymousReceiver(viewer: User): Boolean {
        validateRoomMessage()

        return receiverUsingAnonymousProfile(viewer = viewer)
    }

    fun receiverSchoolName(viewer: User): String? {
        validateRoomMessage()

        if (viewer.isMeSender(senderId = sender.id)) {
            return receiver.school.name
        }

        if (receiverUsingAnonymousProfile(viewer = viewer)) {
            return null
        }

        return sender.school.name
    }

    fun receiverGrade(viewer: User): Int? {
        validateRoomMessage()

        if (viewer.isMeSender(senderId = sender.id)) {
            return receiver.grade
        }

        if (receiverUsingAnonymousProfile(viewer = viewer)) {
            return null
        }

        return sender.grade
    }

    fun receiverClassNumber(viewer: User): Int? {
        validateRoomMessage()

        if (viewer.isMeSender(senderId = sender.id)) {
            return receiver.classNumber
        }

        if (receiverUsingAnonymousProfile(viewer = viewer)) {
            return null
        }

        return sender.classNumber
    }

    fun isMeBookmarked(viewer: User): Boolean {
        if (viewer.isMeSender(senderId = sender.id)) {
            return isSenderBookmarked
        }
        return isReceiverBookmarked
    }

    fun isBlockedByReceiver(viewer: User): Boolean {
        if (viewer.isMeSender(senderId = sender.id)) {
            return isBlocked
        }

        return false
    }

    fun isReportedByReceiver(viewer: User): Boolean {
        if (viewer.isMeSender(senderId = sender.id)) {
            return isReported
        }

        return false
    }

    fun isReceiverEver(viewer: User): Boolean {
        if (viewer.isMeReceiver(receiverId = receiver.id)) {
            return sender.isEver()
        }

        return false
    }

    fun receiverProfileImage(viewer: User): String {
        validateRoomMessage()

        if (viewer.isMeSender(senderId = sender.id)) {
            return receiver.profile.iconUrl
        }

        if (receiverUsingAnonymousProfile(viewer = viewer)) {
            return anonymousProfile!!.imageUrl
        }

        return sender.profile.iconUrl
    }

    fun isAbleToAnswer(viewer: User): Boolean {
        if (viewer.isMeReceiver(receiverId = receiver.id)) {
            return true
        }

        return false
    }


}
