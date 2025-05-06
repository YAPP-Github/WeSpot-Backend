package com.wespot.message.service.v2

import com.wespot.auth.service.SecurityUtils
import com.wespot.message.dto.response.MessageV2StatusResponse
import com.wespot.message.port.`in`.MessageV2UsingStatusUseCase
import com.wespot.message.port.out.MessageV2Port
import com.wespot.message.v2.MessageV2
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class MessageV2UsingStatusService(
    private val userPort: UserPort,
    private val messageV2Port: MessageV2Port,
) : MessageV2UsingStatusUseCase {

    @Transactional(readOnly = true)
    override fun getMessageStatus(): MessageV2StatusResponse {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val today = LocalDate.now()
        val yesterday = today.minusDays(1)
        val messages =
            messageV2Port.findAllLastMessageOfRoomByReceiverIdAndFromDate(
                receiverId = loginUser.id,
                from = yesterday
            )
        val countTodaySentMessage = messageV2Port.countTodaySendMessages(senderId = loginUser.id)

        return MessageV2StatusResponse.of(
            isSendAllowed = MessageV2.COUNT_OF_MAX_ABLE_TO_SEND_MESSAGE_PER_DAY == countTodaySentMessage,
            countRemainingMessages = MessageV2.COUNT_OF_MAX_ABLE_TO_SEND_MESSAGE_PER_DAY - countTodaySentMessage,
            countUnReadMessages = messages.filter { !it.isRead(viewer = loginUser) }.size,
            countUnReplayMessages = messages.filter { it.isSentAtSameDate(date = today) }.size
        )
    }

}
