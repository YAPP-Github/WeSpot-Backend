package com.wespot.message.v2

import com.wespot.EventUtils
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.message.MessageContent
import com.wespot.message.event.MessageAnswerEvent
import com.wespot.message.event.ReadMessageEvent
import com.wespot.message.event.CreatedMessageEvent
import com.wespot.user.User
import com.wespot.user.event.UsedAnswerFeatureEvent
import com.wespot.user.message.AnonymousProfile
import org.springframework.http.HttpStatus
import java.time.LocalDate
import java.time.LocalDateTime

data class MessageV2(
    val id: Long,
    val content: MessageContent,
    val sender: User,
    val receiver: User,
    var isReceiverRead: Boolean,
    var readAt: LocalDateTime?,

    var isSenderBlocked: Boolean,
    var isSenderBlockedAt: LocalDateTime?,

    var isReceiverBlocked: Boolean,
    var isReceiverBlockedAt: LocalDateTime?,

    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,

    var isSenderDeleted: Boolean,
    var senderDeletedAt: LocalDateTime?,

    var isReceiverDeleted: Boolean,
    var receiverDeletedAt: LocalDateTime?,

    val messageRoomId: Long?,
    val messageRoomOwnerId: Long,

    var isSenderBookmarked: Boolean,
    var isReceiverBookmarked: Boolean,

    val anonymousProfile: AnonymousProfile?,
) {

    companion object {

        //        const val COUNT_OF_MAX_ABLE_TO_SEND_MESSAGE_PER_DAY=3
        const val COUNT_OF_MAX_ABLE_TO_SEND_MESSAGE_PER_DAY = 100 // 개발하는 동안 100개로 유지

        fun createInitial(
            content: String,
            sender: User,
            receiver: User,
            anonymousProfile: AnonymousProfile?,
            isAlreadyExistsRoomTalkWithThisReceiverWithRealName: (User, User) -> Boolean,
            alreadyUsedMessageOnToday: Int,
            savedMessageFunction: (MessageV2) -> MessageV2
        ): MessageV2 {
            if (!isAbleToUseMessage(sender, receiver)) {
                throw CustomException(
                    HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "쪽지 기능이 비활성화 되어 있는 유저가 존재합니다."
                )
            }
            if (COUNT_OF_MAX_ABLE_TO_SEND_MESSAGE_PER_DAY <= alreadyUsedMessageOnToday) {
                throw CustomException(
                    HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "하루에 쪽지는 3개만 보낼 수 있습니다."
                )
            }

            if (anonymousProfile == null && isAlreadyExistsRoomTalkWithThisReceiverWithRealName.invoke(
                    sender,
                    receiver
                )
            ) {
                throw CustomException(
                    message = "해당 유저와 실명으로 대화를 나눈 기록이 이미 존재합니다.",
                    status = HttpStatus.BAD_REQUEST,
                    view = ExceptionView.TOAST,
                )
            }

            val message = MessageV2(
                id = 0,
                content = MessageContent.from(content),
                sender = sender,
                receiver = receiver,
                isReceiverRead = false,
                readAt = null,

                isSenderBlocked = false,
                isSenderBlockedAt = null,

                isReceiverBlocked = false,
                isReceiverBlockedAt = null,

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

            val savedMessage = savedMessageFunction.invoke(message)
            val createdMessageEvent = CreatedMessageEvent(
                sender = sender,
                senderAnonymousProfile = anonymousProfile,
                receiver = receiver,
                receiverAnonymousProfile = null,
                message = savedMessage
            )

            EventUtils.publish(createdMessageEvent)
            return savedMessage
        }

        private fun isAbleToUseMessage(user1: User, user2: User): Boolean {
            return user1.isEnableMessage() && user2.isEnableMessage()
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
        return viewer.isMeReceiver(receiverId = receiver.id)
    }

    fun isSent(viewer: User): Boolean {
        return viewer.isMeSender(senderId = sender.id)
    }

    fun isContainsOf(roomMessage: MessageV2): Boolean {
        validateMessageDetail()
        return roomMessage.id == this.messageRoomId
    }

    fun isRead(viewer: User): Boolean {
        if (viewer.isMeSender(senderId = sender.id)) {
            return true
        }

        return isReceiverRead
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

    fun isMeAnonymous(viewer: User): Boolean {
        validateRoomMessage()

        if (viewer.isMeReceiver(receiverId = receiver.id)) {
            return false
        }

        return anonymousProfile != null
    }

    fun isReceiverAnonymous(viewer: User): Boolean {
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

    fun isBlockedByMe(viewer: User): Boolean {
        if (viewer.isMeSender(senderId = sender.id)) {
            return isSenderBlocked
        }

        return isReceiverBlocked
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

    fun isAbleToAnswer(viewer: User, alreadyUsedMessageOnToday: Int): Boolean {
        return alreadyUsedMessageOnToday < COUNT_OF_MAX_ABLE_TO_SEND_MESSAGE_PER_DAY
            && viewer.isMeReceiver(receiverId = receiver.id)
            && isAbleToUseMessage(sender, receiver)
    }

    fun isSameUserProfileAndNotAnonymous(viewer: User): Boolean {
        return isRoom() && viewer.isMeSender(senderId = sender.id) && anonymousProfile == null
    }

    fun isSameAnonymousProfile(anonymousProfileId: Long): Boolean {
        if (anonymousProfile == null) {
            return false
        }

        return anonymousProfile.id == anonymousProfileId
    }

    fun bookmark(viewer: User) {
        if (viewer.isMeSender(senderId = sender.id)) {
            isSenderBookmarked = !isSenderBookmarked
            return
        }

        isReceiverBookmarked = !isReceiverBookmarked
    }

    fun isAbleToView(viewer: User): Boolean {
        return viewer.isMeSender(senderId = sender.id) || viewer.isMeReceiver(receiverId = receiver.id)
    }

    fun answerMessage(viewer: User, alreadyUsedMessageOnToday: Int, content: MessageContent): MessageV2 {
        if (!isAbleToUseMessage(sender, receiver)) {
            throw CustomException(
                message = "쪽지 기능이 비활성화 되어 있는 유저가 존재합니다.",
                status = HttpStatus.BAD_REQUEST,
                view = ExceptionView.TOAST,
            )
        }
        if (viewer.isMeSender(senderId = sender.id)) {
            throw CustomException(
                message = "받은 쪽지에 대해서만 답장할 수 있습니다.",
                status = HttpStatus.BAD_REQUEST,
                view = ExceptionView.TOAST,
            )
        }

        if (alreadyUsedMessageOnToday >= COUNT_OF_MAX_ABLE_TO_SEND_MESSAGE_PER_DAY) {
            throw CustomException(
                message = "하루에 쪽지는 3개만 보낼 수 있습니다.",
                status = HttpStatus.BAD_REQUEST,
                view = ExceptionView.TOAST,
            )
        }

        val newMessage = MessageV2(
            id = 0L,
            content = content,
            sender = receiver,
            receiver = sender,
            isReceiverRead = false,
            readAt = null,

            isSenderBlocked = false,
            isSenderBlockedAt = null,

            isReceiverBlocked = false,
            isReceiverBlockedAt = null,

            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),

            isSenderDeleted = false,
            senderDeletedAt = null,

            isReceiverDeleted = false,
            receiverDeletedAt = null,

            messageRoomId = messageRoomId(),
            messageRoomOwnerId = messageRoomOwnerId,

            isSenderBookmarked = false,
            isReceiverBookmarked = false,

            anonymousProfile = anonymousProfile,
        )

        EventUtils.publish(UsedAnswerFeatureEvent(user = viewer))
        EventUtils.publish(
            MessageAnswerEvent(
                sender = receiver,
                senderAnonymousProfile = if (isUserOwner(receiver)) anonymousProfile else null,
                receiver = sender,
                receiverAnonymousProfile = if (isUserOwner(sender)) anonymousProfile else null,
                message = newMessage
            )
        )

        return newMessage
    }

    private fun isUserOwner(user: User): Boolean {
        return user.id == messageRoomOwnerId
    }

    private fun messageRoomId(): Long? {
        if (isRoom()) {
            return id
        }

        return messageRoomId
    }

    fun read(viewer: User) {
        if (viewer.isMeSender(senderId = sender.id)) {
            return
        }

        if (!viewer.isMeReceiver(receiverId = receiver.id)) {
            return
        }

        if (isReceiverRead) {
            return
        }

        readAt = LocalDateTime.now()
        isReceiverRead = true

        val receiverOfViewer = if (viewer.isSameUser(sender)) receiver else sender
        EventUtils.publish(
            ReadMessageEvent(
                sender = receiverOfViewer,
                senderAnonymousProfile = if (isUserOwner(receiverOfViewer)) anonymousProfile else null,
                receiver = viewer,
                receiverAnonymousProfile = if (isUserOwner(viewer)) anonymousProfile else null,
                message = this,
            )
        )
    }

    fun delete(deleter: User): MessageV2 {
        val alreadyDeleted = isDeleted(viewer = deleter)

        if (alreadyDeleted) {
            throw CustomException(
                message = "이미 삭제된 쪽지입니다.",
                status = HttpStatus.BAD_REQUEST,
                view = ExceptionView.TOAST,
            )
        }

        if (deleter.isMeSender(senderId = sender.id)) {
            isSenderDeleted = true
            senderDeletedAt = senderDeletedAt ?: LocalDateTime.now()
            return this
        }

        isReceiverDeleted = true
        receiverDeletedAt = receiverDeletedAt ?: LocalDateTime.now()
        return this
    }

    fun isDeleted(viewer: User): Boolean {
        if (viewer.isMeSender(senderId = sender.id)) {
            return isSenderDeleted
        }

        return isReceiverDeleted
    }

    fun block(viewer: User) {
        if (!isRoom()) {
            throw CustomException(
                message = "차단은 쪽지방에서만 진행 가능합니다.",
                status = HttpStatus.BAD_REQUEST,
                view = ExceptionView.TOAST,
            )
        }

        if (viewer.isMeSender(senderId = sender.id)) {
            isSenderBlocked = !isSenderBlocked
            isSenderBlockedAt = if (isSenderBlocked) LocalDateTime.now() else null
            return
        }

        isReceiverBlocked = !isReceiverBlocked
        isReceiverBlockedAt = if (isReceiverBlocked) LocalDateTime.now() else null
    }

    fun isSentAtSameDate(date: LocalDate): Boolean {
        return date == createdAt.toLocalDate()
    }

    fun myProfileImage(viewer: User): String {
        validateRoomMessage()

        if (viewer.isMeReceiver(receiverId = receiver.id)) {
            return receiver.profile.iconUrl
        }

        if (isMeAnonymous(viewer)) {
            return anonymousProfile!!.imageUrl
        }

        return sender.profile.iconUrl
    }

    fun myName(viewer: User): String {
        validateRoomMessage()

        if (viewer.isMeReceiver(receiverId = receiver.id)) {
            return receiver.name
        }

        if (isMeAnonymous(viewer)) {
            return anonymousProfile!!.name
        }

        return sender.name
    }

    fun mySchoolName(viewer: User): String? {
        validateRoomMessage()

        if (viewer.isMeReceiver(receiverId = receiver.id)) {
            return receiver.school.name
        }

        if (isMeAnonymous(viewer)) {
            return null
        }

        return sender.school.name
    }

    fun myGrade(viewer: User): Int? {
        validateRoomMessage()

        if (viewer.isMeReceiver(receiverId = receiver.id)) {
            return receiver.grade
        }

        if (isMeAnonymous(viewer)) {
            return null
        }

        return sender.grade
    }

    fun myClassNumber(viewer: User): Int? {
        validateRoomMessage()

        if (viewer.isMeReceiver(receiverId = receiver.id)) {
            return receiver.classNumber
        }

        if (isMeAnonymous(viewer)) {
            return null
        }

        return sender.classNumber
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MessageV2) return false
        if (id != 0L && other.id != 0L) {
            return id == other.id
        }

        return createdAt == other.createdAt && content == other.content
    }

    override fun hashCode(): Int {
        if (id != 0L) {
            return id.hashCode()
        }

        return 31 * createdAt.hashCode() + content.hashCode()
    }

}
