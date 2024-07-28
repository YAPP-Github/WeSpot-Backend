package com.wespot.message.service

import com.wespot.message.port.out.MessagePort
import com.wespot.user.User

object MessageSendValidator {

    fun validateSendMessageLimit(user: User, messagePort: MessagePort): Int {
        val sendMessageCount = messagePort.sendMessageCount(user.id)
        require(sendMessageCount < 3) { "하루에 3개까지 작성 가능해요" }
        return sendMessageCount
    }

    fun validateAlreadySentMessageToday(
        senderId: Long,
        receiverId: Long,
        messagePort: MessagePort
    ) {
        require(!messagePort.hasSentMessageToday(senderId, receiverId)) { "오늘 이미 해당 수신자에게 메시지를 보냈습니다." }
    }

}
