package com.wespot.message.v2

import com.wespot.EventUtils
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.message.MessageContent
import com.wespot.message.event.MessageAnswerEvent
import com.wespot.message.event.ReadMessageByReceiverEvent
import com.wespot.message.event.ReceivedMessageEvent
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

        const val COUNT_OF_MAX_ABLE_TO_SEND_MESSAGE_PER_DAY = 3

        fun createInitial(
            content: String,
            sender: User,
            receiver: User,
            anonymousProfile: AnonymousProfile?,
            alreadyUsedMessageOnToday: Int,
            savedMessageFunction: (MessageV2) -> MessageV2
        ): MessageV2 {
            if (COUNT_OF_MAX_ABLE_TO_SEND_MESSAGE_PER_DAY <= alreadyUsedMessageOnToday) {
                throw CustomException(
                    HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "하루에 쪽지는 3개만 보낼 수 있습니다."
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
            EventUtils.publish(ReceivedMessageEvent(receiver = receiver, messageId = savedMessage.id))

            return savedMessage
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
            return isReceiverBlocked
        }

        if (viewer.isMeReceiver(receiverId = receiver.id)) {
            return isSenderBlocked
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

    fun answerMessage(viewer: User, content: MessageContent): MessageV2 {
        if (viewer.isMeSender(senderId = sender.id)) {
            throw CustomException(
                message = "받은 쪽지에 대해서만 답장할 수 있습니다.",
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
        EventUtils.publish(MessageAnswerEvent(sender = receiver, receiver = sender, message = this))

        return newMessage
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

        EventUtils.publish(
            ReadMessageByReceiverEvent(
                sender = sender,
                receiver = receiver,
                messageId = id,
                beforeIsReceiverRead = false,
            )
        )
        readAt = LocalDateTime.now()
        isReceiverRead = true
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
