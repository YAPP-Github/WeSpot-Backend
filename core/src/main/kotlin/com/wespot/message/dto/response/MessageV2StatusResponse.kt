package com.wespot.message.dto.response

data class MessageV2StatusResponse(
    val isSendAllowed: Boolean,
    val countRemainingMessages: Int,
    val countUnReadMessages: Int,
    val countUnReplayMessages: Int,
    val isReceivedAllowed: Boolean,
) {

    companion object {

        fun of(
            isSendAllowed: Boolean,
            countRemainingMessages: Int,
            countUnReadMessages: Int,
            countUnReplayMessages: Int,
            isReceivedAllowed: Boolean
        ): MessageV2StatusResponse {
            return MessageV2StatusResponse(
                isSendAllowed = isSendAllowed,
                countRemainingMessages = countRemainingMessages,
                countUnReadMessages = countUnReadMessages,
                countUnReplayMessages = countUnReplayMessages,
                isReceivedAllowed= isReceivedAllowed,
            )
        }

    }

}
