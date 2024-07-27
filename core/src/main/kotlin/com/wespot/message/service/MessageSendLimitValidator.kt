package com.wespot.message.service

import com.wespot.message.port.out.MessagePort
import com.wespot.user.User

object MessageSendLimitValidator {

    fun validateSendMessageLimit(user: User, messagePort: MessagePort): Int {
        val sendMessageCount = messagePort.sendMessageCount(user.id)
        require(sendMessageCount < 3) { "하루에 3개까지 작성 가능해요" }
        return sendMessageCount
    }
}
