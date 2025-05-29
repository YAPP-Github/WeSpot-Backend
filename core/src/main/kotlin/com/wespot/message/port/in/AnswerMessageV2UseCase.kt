package com.wespot.message.port.`in`

import com.wespot.message.dto.request.AnswerMessageRequest

interface AnswerMessageV2UseCase {

    fun answerMessage(messageRoomId: Long, answerMessageRequest: AnswerMessageRequest)

}
