package com.wespot.user.service

import com.wespot.auth.service.SecurityUtils.getLoginUser
import com.wespot.message.port.out.MessagePort
import com.wespot.message.service.MessageFinder.findMessageById
import com.wespot.message.service.MessageFinder.findUserById
import com.wespot.user.User
import com.wespot.user.block.BlockedUser
import com.wespot.user.dto.response.BlockedUserResponse
import com.wespot.user.port.`in`.BlockedUserUseCase
import com.wespot.user.port.out.BlockedUserPort
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BlockedUserService(
    private val blockedUserPort: BlockedUserPort,
    private val messagePort: MessagePort,
    private val userPort: UserPort,
): BlockedUserUseCase {

    override fun blockedUser(messageId: Long): BlockedUserResponse {
        val (loginUser, blockedUser) = findAndValidateUsers(messageId)
        val isAlreadyBlocked = blockedUserPort.existsByBlockerIdAndBlockedIdAndMessageId(
            blockerId = loginUser.id,
            blockedId = blockedUser.id,
            messageId = messageId
        )
        check(!isAlreadyBlocked) { "이미 차단된 사용자입니다." }

        val createBlockedUser = BlockedUser.create(
            blockerId = loginUser.id,
            blockedId = blockedUser.id,
            messageId = messageId
        )
        val saveBlockedUser = blockedUserPort.save(createBlockedUser)
        return BlockedUserResponse.of(saveBlockedUser.id)
    }

    override fun unblockedUser(messageId: Long) {
        val (loginUser, blockedUser) = findAndValidateUsers(messageId)
        blockedUserPort.deleteByBlockerIdAndBlockedIdAndMessageId(
            blockerId = loginUser.id,
            blockedId = blockedUser.id,
            messageId = messageId
        )
    }

    private fun findAndValidateUsers(messageId: Long): Pair<User, User> {
        val loginUser = getLoginUser(userPort = userPort)
        val message = findMessageById(id = messageId, messagePort = messagePort)
        message.validateReceivedMessage(loginUser)
        val blockedUser = findUserById(id = message.senderId, userPort = userPort)
        return Pair(loginUser, blockedUser)
    }

}
