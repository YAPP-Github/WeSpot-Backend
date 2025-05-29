package com.wespot.message.dto.response

import com.wespot.message.v2.MessageV2

data class MessageV2StatusResponse(
    val isSendAllowed: Boolean,
    val countRemainingMessages: Int,
    val countUnReadMessages: Int,
    val countUnReplayMessages: Int,
) {

    companion object {

        fun of(
            isSendAllowed: Boolean,
            countRemainingMessages: Int,
            countUnReadMessages: Int,
            countUnReplayMessages: Int,
        ): MessageV2StatusResponse {
            return MessageV2StatusResponse(
                isSendAllowed = isSendAllowed,
                countRemainingMessages = countRemainingMessages,
                countUnReadMessages = countUnReadMessages,
                countUnReplayMessages = countUnReplayMessages,
            )
        }

    }

}
