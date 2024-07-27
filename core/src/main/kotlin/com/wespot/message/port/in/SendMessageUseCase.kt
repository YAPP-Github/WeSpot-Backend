package com.wespot.message.port.`in`

import com.wespot.message.dto.request.SendMessageRequest
import com.wespot.message.dto.response.SendMessageResponse


interface SendMessageUseCase {

    fun send(sendMessageRequest: SendMessageRequest): SendMessageResponse

}
