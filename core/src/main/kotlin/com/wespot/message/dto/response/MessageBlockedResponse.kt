package com.wespot.message.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.message.Message
import com.wespot.school.School
import com.wespot.user.Profile
import com.wespot.user.User
import com.wespot.user.dto.response.UserResponse
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MessageBlockedResponse(
    val id: Long,
    val senderName: String,
    val senderProfile: Profile,
    val receiver: UserResponse,
    val content: String,
    val receivedAt: String?,
    val isRead: Boolean,
    val isBlocked: Boolean,
    val isReported: Boolean,
    val readAt: String?
){
    companion object {

        fun from(
            message: Message,
            senderProfile: Profile,
            receiver: User,
            school: School,
            isBlocked: Boolean
        ): MessageBlockedResponse {
            return MessageBlockedResponse(
                id = message.id,
                senderName = message.senderName,
                senderProfile = senderProfile,
                receiver = UserResponse.from(receiver, school.name),
                content = message.content,
                receivedAt = message.receivedAt?.toString(),
                isRead = message.isReceiverRead,
                isBlocked = isBlocked,
                isReported = message.isReported,
                readAt = message.readAt?.toString()
            )
        }

    }
}
