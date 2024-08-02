package com.wespot.notification

import com.wespot.user.User
import java.time.LocalDate
import java.time.LocalDateTime

data class Notification(
    val id: Long,
    val userId: Long,
    val type: NotificationType,
    val date: LocalDate,
    val targetId: Long,
    val content: String,
    var isRead: Boolean,
    var readAt: LocalDateTime,
    var isEnabled: Boolean,
    val createdAt: LocalDateTime,
) {

    companion object {

        fun createVoteInitialState(
            userId: Long,
            type: NotificationType,
            date: LocalDate,
            content: String,
        ): Notification {
            validateVoteType(type)
            return Notification(
                id = 0L,
                userId = userId,
                type = type,
                date = date,
                targetId = 0,
                content = content,
                isRead = false,
                readAt = LocalDateTime.now(),
                isEnabled = true,
                createdAt = LocalDateTime.now()
            )
        }

        private fun validateVoteType(type: NotificationType) {
            require(
                type == NotificationType.VOTE
                    || type == NotificationType.VOTE_RESULT
                    || type == NotificationType.VOTE_RECEIVED
            ) { "투표 관련 알림이 아닙니다." }
        }

        fun createMessageInitialState(
            userId: Long,
            type: NotificationType,
            targetId: Long,
            content: String,
        ): Notification {
            validateMessageType(type)
            return Notification(
                id = 0L,
                userId = userId,
                type = type,
                date = LocalDate.now(),
                targetId = targetId,
                content = content,
                isRead = false,
                readAt = LocalDateTime.now(),
                isEnabled = true,
                createdAt = LocalDateTime.now()
            )
        }

        private fun validateMessageType(type: NotificationType) {
            require(
                type == NotificationType.MESSAGE
                    || type == NotificationType.MESSAGE_SENT
                    || type == NotificationType.MESSAGE_RECEIVED
            ) { "쪽지 관련 알림이 아닙니다." }
        }

    }

    fun isVoteType(): Boolean {
        return type.isVote()
    }

    fun isMessageType(): Boolean {
        return type.isMessage()
    }

    fun read(reader: User) {
        require(reader.id == userId) { "알림 수신자만 알림을 조회할 수 있습니다." }
        this.isRead = true
        this.readAt = LocalDateTime.now()
    }

    fun disableVoteNotification(enabledDate: LocalDate) {
        if (NotificationType.VOTE != type || createdAt.toLocalDate() == enabledDate) {
            return
        }
        isEnabled = false
    }

    fun disableMessageNotification() {
        if (NotificationType.MESSAGE != type) {
            return
        }
        isEnabled = false
    }

}
