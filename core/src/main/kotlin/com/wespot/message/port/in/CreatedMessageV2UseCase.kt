package com.wespot.message.port.`in`

import com.wespot.message.MessageV2
import com.wespot.message.dto.request.CreatedMessageV2Request

interface CreatedMessageV2UseCase {

    fun createMessage(createdMessageV2Request: CreatedMessageV2Request): MessageV2

}
