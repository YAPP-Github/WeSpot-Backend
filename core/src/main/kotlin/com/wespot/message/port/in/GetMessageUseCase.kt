package com.wespot.message.port.`in`

import com.wespot.message.dto.response.MessageListResponse
import com.wespot.message.dto.response.MessageResponse
import com.wespot.message.dto.response.MessageSimpleListResponse
import com.wespot.message.dto.response.SendMessageStatusResponse

interface GetMessageUseCase {

    fun getMessage(messageId: Long): MessageResponse

    fun getReceivedMessages(cursorId: Long): MessageListResponse

    fun getSendMessages(cursorId: Long): MessageListResponse

    fun status(): SendMessageStatusResponse

    fun getScheduledMessages(): MessageSimpleListResponse

}
