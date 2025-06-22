package com.wespot.message.v2

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.message.MessageContent
import com.wespot.user.User
import com.wespot.user.message.AnonymousProfile
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class MessageRoom(
    val viewer: User,
    val roomMessage: MessageV2,
    val messages: MessageDetails,
) {

    companion object {

        fun of(
            viewer: User,
            alreadyUsedMessageOnToday: Int,
            roomMessage: MessageV2,
            messages: List<MessageV2>
        ): MessageRoom {
            if (!roomMessage.isRoom()) {
                throw CustomException(
                    message = "쪽지 방이 아닙니다.",
                    status = HttpStatus.BAD_REQUEST,
                    view = ExceptionView.TOAST,
                )
            }

            if (!roomMessage.isAbleToView(viewer = viewer)) {
                throw CustomException(
                    message = "해당 쪽지 방을 볼 수 있는 권한이 존재하지 않습니다.",
                    status = HttpStatus.FORBIDDEN,
                    view = ExceptionView.TOAST,
                )
            }

            return MessageRoom(
                viewer = viewer,
                roomMessage = roomMessage,
                messages = MessageDetails.of(
                    viewer = viewer,
                    alreadyUsedMessageOnToday = alreadyUsedMessageOnToday,
                    messages = listOf(roomMessage) + messages.filter { it.isContainsOf(roomMessage = roomMessage) }
                )
            )
        }

        fun of(viewer: User, alreadyUsedMessageOnToday: Int, allMessagesOfRoom: List<MessageV2>): MessageRoom {
            val roomMessage = allMessagesOfRoom.find { it.isRoom() } ?: throw CustomException(
                message = "쪽지 방이 존재하지 않습니다.",
                status = HttpStatus.BAD_REQUEST,
                view = ExceptionView.TOAST,
            )

            if (!roomMessage.isAbleToView(viewer = viewer)) {
                throw CustomException(
                    message = "해당 쪽지 방을 볼 수 있는 권한이 존재하지 않습니다.",
                    status = HttpStatus.FORBIDDEN,
                    view = ExceptionView.TOAST,
                )
            }

            val messageDetails = allMessagesOfRoom.filter { !it.isRoom() }

            return MessageRoom(
                viewer = viewer,
                roomMessage = roomMessage,
                messages = MessageDetails.of(
                    viewer = viewer,
                    alreadyUsedMessageOnToday = alreadyUsedMessageOnToday,
                    messages = listOf(roomMessage) + messageDetails.filter { it.isContainsOf(roomMessage) }
                )
            )
        }

    }

    fun isExistsUnReadMessage(): Boolean {
        return !roomMessage.isRead(viewer = viewer) || messages.isExistsUnreadMessage()
    }

    fun id(): Long {
        return roomMessage.id
    }

    fun isMeUsingAnonymous(): Boolean {
        return roomMessage.isMeAnonymous(viewer = viewer)
    }

    fun isReceiverUsingAnonymous(): Boolean {
        return roomMessage.isReceiverAnonymous(viewer = viewer)
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

    fun isBlockedByMe(): Boolean {
        return roomMessage.isBlockedByMe(viewer = viewer)
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

    fun isAbleToAnswer(): Boolean {
        return messages.isAbleToAnswer()
    }

    fun isSameUserProfileAndNotAnonymous(viewer: User): Boolean {
        return roomMessage.isSameUserProfileAndNotAnonymous(viewer = viewer)
    }

    fun isSameAnonymousProfile(anonymousProfileId: Long): Boolean {
        return roomMessage.isSameAnonymousProfile(anonymousProfileId = anonymousProfileId)
    }

    fun anonymousProfile(): AnonymousProfile? {
        return roomMessage.anonymousProfile
    }

    fun answer(sender: User, alreadyUsedMessageOnToday: Int, content: String): MessageV2 {
        val validatedContent = MessageContent.from(content = content)

        val answerMessage = messages.answer(
            sender = sender,
            alreadyUsedMessageOnToday = alreadyUsedMessageOnToday,
            content = validatedContent
        )

        return answerMessage
    }

    fun deleteMessage(messageId: Long): MessageV2 {
        val excludedDeletedMessage = messageDetailsAsList()

        if (excludedDeletedMessage.size <= 1) {
            throw CustomException(
                message = "해당 쪽지를 삭제하면 쪽지 방에 남은 쪽지가 존재하지 않습니다.",
                status = HttpStatus.BAD_REQUEST,
                view = ExceptionView.TOAST,
            )
        }

        return messages.deleteMessage(
            viewer = viewer,
            messageId = messageId
        )
    }

    fun messageDetailsAsList(): List<MessageDetail> {
        return messages.asList(viewer = viewer)
    }

    fun readUnreadMessages(): List<MessageV2> {
        return messages.readUnreadMessage(viewer = viewer)
    }

    fun senderProfileImage(): String {
        return roomMessage.myProfileImage(viewer = viewer)
    }

    fun senderName(): String {
        return roomMessage.myName(viewer = viewer)
    }

    fun senderSchoolName(): String? {
        return roomMessage.mySchoolName(viewer = viewer)
    }

    fun senderGrade(): Int? {
        return roomMessage.myGrade(viewer = viewer)
    }

    fun senderClassNumber(): Int? {
        return roomMessage.myClassNumber(viewer = viewer)
    }

}
