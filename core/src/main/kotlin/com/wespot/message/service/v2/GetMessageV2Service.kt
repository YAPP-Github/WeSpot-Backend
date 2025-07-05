package com.wespot.message.service.v2

import com.wespot.auth.service.SecurityUtils
import com.wespot.message.dto.response.MessageV2DetailsResponse
import com.wespot.message.dto.response.MessageV2OverviewResponse
import com.wespot.message.port.`in`.GetMessageV2UseCase
import com.wespot.message.port.out.MessageV2Port
import com.wespot.message.v2.MessageRoom
import com.wespot.message.v2.MessageRooms
import com.wespot.message.v2.MessageV2
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetMessageV2Service(
    private val messageV2Port: MessageV2Port,
    private val userPort: UserPort
) : GetMessageV2UseCase {

    @Transactional(readOnly = true)
    override fun getMessageOverview(): List<MessageV2OverviewResponse> {
        return getCompleteMessageOverviewByFinder(
            sentMessageRoomsFinder = { senderId ->
                messageV2Port.findAllMessageRoomBySenderId(senderId = senderId)
            },
            receivedMessageRoomsFinder = { receiverId ->
                messageV2Port.findAllMessageRoomByReceiverId(receiverId = receiverId)
            }
        )
    }

    private fun getCompleteMessageOverviewByFinder(
        sentMessageRoomsFinder: (Long) -> List<MessageV2>,
        receivedMessageRoomsFinder: (Long) -> List<MessageV2>
    ): List<MessageV2OverviewResponse> {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)

        val sentMessageRooms =
            sentMessageRoomsFinder.invoke(loginUser.id)
        val receivedMessageRooms =
            receivedMessageRoomsFinder.invoke(loginUser.id)

        val rooms: List<MessageV2> = sentMessageRooms + receivedMessageRooms

        val messageRoomIds: List<Long> = rooms.map { it.id }
        val messageDetails = messageV2Port.findAllLastMessageOfRoomByRoomIdIn(messageRoomIds)
        val alreadyUsedMessageOnToday = messageV2Port.countTodaySendMessages(loginUser.id)

        val messageRooms =
            MessageRooms.createOverview(
                viewer = loginUser,
                rooms = rooms,
                alreadyUsedMessageOnToday = alreadyUsedMessageOnToday,
                messageDetails = messageDetails
            )

        return messageRooms.asList()
            .map { MessageV2OverviewResponse.from(it) }
            .sortedByDescending { it.latestChatTime }
    }

    @Transactional(readOnly = true)
    override fun getBookmarkedMessageOverview(): List<MessageV2OverviewResponse> {
        return getCompleteMessageOverviewByFinder(
            sentMessageRoomsFinder = { senderId ->
                messageV2Port.findAllMessageRoomBySenderIdAndIsSenderBookmarkedTrue(senderId = senderId)
            },
            receivedMessageRoomsFinder = { receiverId ->
                messageV2Port.findAllMessageRoomByReceiverIdAndIsReceiverBookmarkedTrue(receiverId = receiverId)
            }
        )
    }

    @Transactional(readOnly = true)
    override fun getBlockedMessageOverview(): List<MessageV2OverviewResponse> {
        return getCompleteMessageOverviewByFinder(
            sentMessageRoomsFinder = { senderId ->
                messageV2Port.findAllMessageRoomBySenderIdAndIsSenderBlockedTrue(senderId = senderId)
            },
            receivedMessageRoomsFinder = { receiverId ->
                messageV2Port.findAllMessageRoomByReceiverIdAndIsReceiverBlockedTrue(receiverId = receiverId)
            }
        )
    }

    @Transactional
    override fun getMessageDetails(messageId: Long): MessageV2DetailsResponse {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)

        val roomMessage = messageV2Port.findById(id = messageId)
        val messageDetails = messageV2Port.findAllByMessageRoomId(messageRoomId = messageId)
        val alreadyUsedMessageOnToday = messageV2Port.countTodaySendMessages(senderId = loginUser.id)

        val room = MessageRoom.of(
            viewer = loginUser,
            alreadyUsedMessageOnToday = alreadyUsedMessageOnToday,
            roomMessage = roomMessage,
            messages = messageDetails
        )
        val response = MessageV2DetailsResponse.from(room = room)
        val readUnreadMessages = room.readUnreadMessages()
        readUnreadMessages
            .forEach { messageV2Port.save(it) }
        return response
    }

}
