package com.wespot.message.port.`in`

import com.wespot.message.dto.response.MessageV2StatusResponse
import com.wespot.user.User

interface MessageV2UsingStatusUseCase {

    fun getMessageStatus(): MessageV2StatusResponse

    fun getMessageStatus(loginUser: User): MessageV2StatusResponse

}
