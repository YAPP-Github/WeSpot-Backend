package com.wespot.report

import com.google.common.io.ByteArrayDataInput
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.User
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class Report(
    val id: Long,
    val reportType: ReportType,
    val targetId: Long,
    val senderId: Long,
    val receiverId: Long,
    val createdAt: LocalDateTime
) {

    companion object {

        fun of(
            reportType: ReportType,
            targetId: Long,
            sender: User,
            receiver: User
        ): Report {
            validate(sender, receiver)
            return Report(
                id = 0,
                reportType = reportType,
                targetId = targetId,
                senderId = sender.id,
                receiverId = receiver.id,
                createdAt = LocalDateTime.now()
            )
        }

        private fun validate(sender: User, receiver: User) {
            if (sender.id == receiver.id) {
                throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "본인이 본인을 신고할 수 없습니다.")
            }
        }

    }

    fun isMessageReport(): Boolean {
        return reportType == ReportType.MESSAGE
    }

    fun isVoteReport(): Boolean {
        return reportType == ReportType.VOTE
    }

    fun isSameReport(otherReport: Report): Boolean {
        return reportType == otherReport.reportType
            && targetId == otherReport.targetId
            && senderId == otherReport.senderId
            && receiverId == otherReport.receiverId
    }

}
