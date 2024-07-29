package com.wespot.user.service

import com.wespot.auth.service.SecurityUtils.getLoginUser
import com.wespot.message.port.out.MessagePort
import com.wespot.message.service.MessageFinder.findMessageById
import com.wespot.message.service.MessageFinder.findUserById
import com.wespot.user.block.BlockedUser
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

    override fun blockedUser(messageId: Long): Boolean {
        val loginUser = getLoginUser(userPort = userPort)
        val message = findMessageById(id = messageId, messagePort = messagePort)
        message.validateReceivedMessage(loginUser)
        val blockedUser = findUserById(id = message.senderId, userPort = userPort)

        return when (isBlocked(loginUser.id, blockedUser.id)) {
            true -> unblockUser(loginUser.id, blockedUser.id)
            false -> blockUser(loginUser.id, blockedUser.id)
        }
    }

    private fun isBlocked(blockerId: Long, blockedId: Long): Boolean {
        return blockedUserPort.existsByBlockerIdAndBlockedId(blockerId, blockedId)
    }

    private fun blockUser(blockerId: Long, blockedId: Long): Boolean {
        val blockedUser = BlockedUser.create(blockerId, blockedId)
        blockedUserPort.save(blockedUser)
        return true
    }

    private fun unblockUser(blockerId: Long, blockedId: Long): Boolean {
        blockedUserPort.deleteByBlockerIdAndBlockedId(blockerId, blockedId)
        return false
    }
}
