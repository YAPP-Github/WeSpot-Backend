package com.wespot.message.port.`in`

import com.wespot.message.dto.response.MessageV2DetailsResponse
import com.wespot.message.dto.response.MessageV2OverviewResponse

interface GetMessageV2UseCase {

    fun getMessageOverview(): List<MessageV2OverviewResponse>

    fun getBookmarkedMessageOverview(): List<MessageV2OverviewResponse>

    fun getBlockedMessageOverview(): List<MessageV2OverviewResponse>

    fun getMessageDetails(messageId: Long): MessageV2DetailsResponse

}
