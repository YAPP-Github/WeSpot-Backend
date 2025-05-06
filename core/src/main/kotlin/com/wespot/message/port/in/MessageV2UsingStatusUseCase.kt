package com.wespot.message.port.`in`

import com.wespot.message.dto.response.MessageV2StatusResponse

interface MessageV2UsingStatusUseCase {

    fun getMessageStatus(): MessageV2StatusResponse

}
