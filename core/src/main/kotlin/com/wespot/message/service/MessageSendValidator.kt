package com.wespot.message.service

import com.wespot.message.port.out.MessagePort
import com.wespot.user.User
import com.wespot.user.port.out.BlockedUserPort

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

    fun validateUserBlockStatus(senderId: Long, receiverId: Long, blockedUserPort: BlockedUserPort) {
        check(!blockedUserPort.existsByBlockerIdAndBlockedId(blockerId = senderId, blockedId = receiverId) &&
            !blockedUserPort.existsByBlockerIdAndBlockedId(blockerId = receiverId, blockedId = senderId)) {
            "차단된 유저에게 메시지를 보낼 수 없습니다."
        }
    }

}
