package com.wespot.message.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.wespot.message.Message
import com.wespot.school.School
import com.wespot.user.User
import com.wespot.user.dto.response.UserResponse

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MessageResponse(
    val id: Long,
    val senderName: String,
    val sender: UserResponse?,
    val receiver: UserResponse,
    val content: String,
    val receivedAt: String?,
    val isRead: Boolean,
    val isBlocked: Boolean,
    val isReported: Boolean,
    val readAt: String?,
    val isAnonymous: Boolean
) {
    companion object {

        fun of(
            message: Message,
            receiver: User,
            sender: User,
            school: School,
            isBlocked: Boolean
        ): MessageResponse {
            return MessageResponse(
                id = message.id,
                senderName = message.senderName,
                sender = if (message.isAnonymous) null else UserResponse.from(sender, school.name),
                receiver = UserResponse.from(receiver, school.name),
                content = message.content.content,
                receivedAt = message.receivedAt?.toString(),
                isRead = message.isReceiverRead,
                isBlocked = isBlocked,
                isReported = message.isReported,
                readAt = message.readAt?.toString(),
                isAnonymous = message.isAnonymous
            )
        }

    }
}
