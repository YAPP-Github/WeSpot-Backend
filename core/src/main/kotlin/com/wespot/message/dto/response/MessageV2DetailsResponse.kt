package com.wespot.message.dto.response

import com.wespot.message.v2.MessageDetail
import com.wespot.message.v2.MessageRoom

data class MessageV2DetailsResponse(
    val thumbnail: String,
    val name: String,
    val messageRoomId: Long,
    val isBookmarked: Boolean,
    val messageDetails: List<MessageDetailResponse>
) {

    data class MessageDetailResponse(
        val id: Long,
        val createdAt: String,
        val content: String,
        val isReceived: Boolean,
        val isSend: Boolean,
        val isAbleToAnswer: Boolean = false,
    ) {

        companion object {
            fun from(message: MessageDetail): MessageDetailResponse {
                return MessageDetailResponse(
                    id = message.message.id,
                    createdAt = message.message.createdAt.toString(),
                    content = message.message.content.content,
                    isReceived = message.isReceived,
                    isSend = message.isSend,
                    isAbleToAnswer = message.isAbleToAnswer,
                )
            }
        }

    }

    companion object {

        fun from(room: MessageRoom): MessageV2DetailsResponse {
            return MessageV2DetailsResponse(
                thumbnail = room.receiverProfileImage(),
                name = room.receiverName(),
                messageRoomId = room.id(),
                isBookmarked = room.isBookmarked(),
                messageDetails = room.messages.asList()
                    .map { MessageDetailResponse.from(it) }
            )
        }

    }

}
