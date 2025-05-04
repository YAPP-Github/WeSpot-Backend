package com.wespot.user.port.out

import com.wespot.user.message.UsedAnswerMessage

interface UsedAnswerMessagePort {

    fun save(usedAnswerMessage: UsedAnswerMessage): UsedAnswerMessage

    fun existsByUserId(userId: Long): Boolean

}
