package com.wespot.message.port.`in`

import com.wespot.message.dto.request.UpdateMessageRequest
import com.wespot.message.dto.response.UpdateMessageResponse

interface ModifyMessageUseCase {

    fun updateMessage(
        messageId: Long,
        updateMessageRequest: UpdateMessageRequest
    ): UpdateMessageResponse

    fun readMessage(messageId: Long)
}
