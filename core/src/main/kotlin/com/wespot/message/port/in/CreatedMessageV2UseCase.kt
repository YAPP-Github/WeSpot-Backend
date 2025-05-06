package com.wespot.message.port.`in`

import com.wespot.message.v2.MessageV2
import com.wespot.message.dto.request.CreatedMessageV2Request
import com.wespot.user.User

interface CreatedMessageV2UseCase {

    fun createMessage(createdMessageV2Request: CreatedMessageV2Request): MessageV2

    fun welcomeMessage(signUpUser: User)

}
