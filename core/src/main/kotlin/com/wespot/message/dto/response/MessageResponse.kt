package com.wespot.message.dto.response

import com.wespot.message.Message
import com.wespot.school.School
import com.wespot.user.User
import java.time.LocalDateTime

data class MessageResponse(
    val id: Long,
    val senderName: String,
    val receiverId: Long,
    val receiverName: String,
    val receiverSchoolName: String,
    val receiverGrade : Int,
    val receiverClassNumber : Int,
    val content: String,
    val receivedAt: LocalDateTime?,
    val isRead: Boolean?,
    val isBlocked: Boolean = false,
    val readAt: String?
){
    companion object {

        fun from(
           message: Message,
           receiver: User,
           school: School
        ): MessageResponse {
            return MessageResponse(
                id = message.id,
                senderName = message.senderName,
                receiverId = message.receiverId,
                receiverName = receiver.name,
                receiverSchoolName = school.name,
                receiverGrade = receiver.grade,
                receiverClassNumber = receiver.classNumber,
                content = message.content,
                receivedAt = message.receivedAt,
                isRead = message.isReceiverRead,
                readAt = message.readAt.toString()
            )
        }


    }
}
