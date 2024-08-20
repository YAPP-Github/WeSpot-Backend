package com.wespot.message.port.`in`

import com.wespot.message.dto.response.*

interface GetMessageUseCase {

    fun getMessage(messageId: Long): MessageResponse

    fun getReceivedMessages(cursorId: Long): MessageListResponse

    fun getSendMessages(cursorId: Long): MessageListResponse

    fun status(): SendMessageStatusResponse

    fun getScheduledMessages(): MessageSimpleListResponse

    fun getBlockedMessages(cursorId: Long): MessageBlockedListResponse

    fun getUnreadMessageCount(): UnreadMessageResponse
}
