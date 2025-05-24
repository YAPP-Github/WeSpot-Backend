package com.wespot.message.dto.response

data class MessageV2OverviewResponses(
    val messages: List<MessageV2OverviewResponse>,
    val hasNext: Boolean,
    val lastCursor: Long?,
) {

    companion object {

//        fun of(
//            messages: List<MessageV2OverviewResponse>,
//            hasNext: Boolean,
//        ): MessageV2OverviewResponses {
//            return MessageV2OverviewResponses(
//                messages = messages,
//                hasNext = hasNext,
//                lastCursor = lastCursor
//            )
//        }
    }

}
