package com.wespot.notification

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import org.springframework.http.HttpStatus
import java.time.LocalDate
import java.time.LocalDateTime

data class Notification(
    val id: Long = 0L,
    val userId: Long,
    val type: NotificationType,
    val date: LocalDate = LocalDate.now(),
    val targetId: Long,
    val title: String,
    val body: String,
    var isRead: Boolean = false,
    var readAt: LocalDateTime? = null,
    var isEnabled: Boolean = true,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {

    companion object {

        private val CAN_DISABLED_NOTIFICATION_VOTE_TYPE = mapOf(
            Pair(
                NotificationType.VOTE,
                { notification: Notification, enabledDate: LocalDate -> notification.date != enabledDate }),
            Pair(
                NotificationType.VOTE_RESULT,
                { notification: Notification, enabledDate: LocalDate ->
                    notification.date != enabledDate &&
                        notification.date != enabledDate.minusDays(1)
                }
            ),
        )

        private val CAN_DISABLED_NOTIFICATION_MESSAGE_TYPE = mapOf(
            Pair(NotificationType.MESSAGE, { _: Notification, _: LocalDate -> true })
        )

        fun createVoteInitialState(
            userId: Long,
            type: NotificationType,
            date: LocalDate,
            title: String,
            body: String,
        ): Notification {
            validateVoteType(type)
            return Notification(
                id = 0L,
                userId = userId,
                type = type,
                date = date,
                targetId = 0,
                title = title,
                body = body,
                isRead = false,
                readAt = LocalDateTime.now(),
                isEnabled = true,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        }

        private fun validateVoteType(type: NotificationType) {
            require(type.isVote()) {
                throw CustomException(
                    HttpStatus.BAD_REQUEST,
                    ExceptionView.TOAST,
                    "투표 관련 알림이 아닙니다."
                )
            }
        }

        fun create(
            userId: Long,
            type: NotificationType,
            targetId: Long,
            title: String,
            body: String,
        ): Notification {
            return Notification(
                userId = userId,
                type = type,
                targetId = targetId,
                title = title,
                body = body
            )
        }

        fun createMessageInitialState(
            userId: Long,
            type: NotificationType,
            targetId: Long,
            title: String,
            body: String,
        ): Notification {
            validateMessageType(type)
            return Notification(
                id = 0L,
                userId = userId,
                type = type,
                date = LocalDate.now(),
                targetId = targetId,
                title = title,
                body = body,
                isRead = false,
                readAt = LocalDateTime.now(),
                isEnabled = true,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        }

        private fun validateMessageType(type: NotificationType) {
            require(type.isMessage()) {
                throw CustomException(
                    HttpStatus.BAD_REQUEST,
                    ExceptionView.TOAST,
                    "쪽지 관련 알림이 아닙니다."
                )
            }
        }

        fun createEventInitialState(
            userId: Long,
            type: NotificationType,
            title: String,
            body: String,
        ): Notification {
            validateEventType(type)

            return Notification(
                id = 0L,
                userId = userId,
                type = type,
                date = LocalDate.now(),
                targetId = 0,
                title = title,
                body = body,
                isRead = false,
                readAt = LocalDateTime.now(),
                isEnabled = true,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        }

        private fun validateEventType(type: NotificationType) {
            require(!type.isVote() && !type.isMessage()) {
                throw CustomException(
                    HttpStatus.BAD_REQUEST,
                    ExceptionView.TOAST,
                    "이벤트 관련 알림이 아닙니다."
                )
            }
        }

    }

    fun read(readerId: Long) {
        require(readerId == userId) {
            throw CustomException(
                HttpStatus.BAD_REQUEST,
                ExceptionView.TOAST,
                "알림 수신자만 알림을 조회할 수 있습니다."
            )
        }
        this.isRead = true
        this.readAt = LocalDateTime.now()
    }

    fun disableVoteNotification(enabledDate: LocalDate) {
        if (isCanNotDisabledVoteType(enabledDate)) {
            return
        }
        isEnabled = false
        isRead = true
    }

    private fun isCanNotDisabledVoteType(enabledDate: LocalDate): Boolean {
        if (!CAN_DISABLED_NOTIFICATION_VOTE_TYPE.containsKey(type)) {
            return true
        }
        CAN_DISABLED_NOTIFICATION_VOTE_TYPE.getValue(type).let { return !it(this, enabledDate) }
    }

    fun disableMessageNotification() {
        if (isCanNotDisabledMessageType()) {
            return
        }
        isEnabled = false
        isRead = true
    }

    private fun isCanNotDisabledMessageType(): Boolean {
        if (!CAN_DISABLED_NOTIFICATION_MESSAGE_TYPE.containsKey(type)) {
            return true
        }
        val ignoredDate = LocalDate.now()
        CAN_DISABLED_NOTIFICATION_MESSAGE_TYPE.getValue(type).let { return !it(this, ignoredDate) }
    }

    fun enableMessageNotification() {
        if (isCanNotDisabledMessageType()) {
            return
        }
        isEnabled = true
    }

}
