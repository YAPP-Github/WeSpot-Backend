package com.wespot.message.service.v2

import com.wespot.auth.service.SecurityUtils
import com.wespot.message.dto.response.AnonymousProfileResponse
import com.wespot.message.port.`in`.GetAnonymousProfileUseCase
import com.wespot.message.port.out.MessageV2Port
import com.wespot.message.v2.MessageRooms
import com.wespot.message.v2.UserProfiles
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetAnonymousProfileService(
    private val userPort: UserPort,
    private val messagePort: MessageV2Port,
) : GetAnonymousProfileUseCase {

    @Transactional(readOnly = true)
    override fun getAnonymousProfileByReceiverId(receiverId: Long): List<AnonymousProfileResponse> {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val messageRooms =
            messagePort.findAllMessageRoomBySenderIdAndReceiverId(senderId = loginUser.id, receiverId = receiverId)
        val messageRoomIds = messageRooms.map { it.id }
        val messageDetails = messagePort.findAllLastMessageOfRoomByRoomIdIn(messageRoomIds)

        val rooms =
            MessageRooms.createOverview(viewer = loginUser, rooms = messageRooms, messageDetails = messageDetails)

        return UserProfiles.of(viewer = loginUser, messageRooms = rooms)
            .asList()
            .map { AnonymousProfileResponse.from(it) }
    }

}

