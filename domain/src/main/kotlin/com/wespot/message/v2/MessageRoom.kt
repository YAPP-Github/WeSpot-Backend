package com.wespot.message.v2

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.User
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class MessageRoom(
    val viewer: User,
    val roomMessage: MessageV2,
    val messages: MessageDetails,
) {

    companion object {

        fun of(viewer: User, roomMessage: MessageV2, messages: List<MessageV2>): MessageRoom {
            if (!roomMessage.isRoom()) {
                throw CustomException(
                    message = "쪽지 방이 아닙니다.",
                    status = HttpStatus.BAD_REQUEST,
                    view = ExceptionView.TOAST,
                )
            }

            return MessageRoom(
                viewer = viewer,
                roomMessage = roomMessage,
                messages = MessageDetails.of(
                    viewer = viewer,
                    messages = listOf(roomMessage) + messages.filter { it.isContainsOf(roomMessage) }
                )
            )
        }

    }

    fun isExistsUnReadMessage(): Boolean {
        return !roomMessage.isRead(viewer = viewer) || messages.isExistsUnreadMessage(viewer = viewer)
    }

    fun id(): Long {
        return roomMessage.id
    }

    fun isAnonymous(): Boolean {
        return roomMessage.isAnonymousReceiver(viewer = viewer)
    }

    fun isViewerOwnerOfMessageRoom(): Boolean {
        return roomMessage.isMeOwnerOfMessageRoom(viewer = viewer)
    }

    fun receiverName(): String {
        return roomMessage.receiverName(viewer = viewer)
    }

    fun receiverSchoolName(): String? {
        return roomMessage.receiverSchoolName(viewer = viewer)
    }

    fun receiverGrade(): Int? {
        return roomMessage.receiverGrade(viewer = viewer)
    }

    fun receiverClassNumber(): Int? {
        return roomMessage.receiverClassNumber(viewer = viewer)
    }

    fun isBookmarked(): Boolean {
        return roomMessage.isMeBookmarked(viewer = viewer)
    }

    fun isBlocked(): Boolean {
        return roomMessage.isBlockedByReceiver(viewer = viewer)
    }

    fun isReceiverEver(): Boolean {
        return roomMessage.isReceiverEver(viewer = viewer)
    }

    fun receiverProfileImage(): String {
        return roomMessage.receiverProfileImage(viewer = viewer)
    }

    fun latestChatTime(): LocalDateTime {
        val roomMessageChatTime: List<LocalDateTime> = listOf(roomMessage.createdAt)

        return (roomMessageChatTime + messages.chatsTime())
            .maxOrNull()!!
    }

    fun isReported(): Boolean {
        return roomMessage.isReportedByReceiver(viewer = viewer)
    }

}
