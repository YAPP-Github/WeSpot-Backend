package com.wespot.message.port.out

import com.wespot.message.MessageV2

interface MessageV2Port {

    fun save(messageV2: MessageV2): MessageV2

    fun countTodaySendMessages(userId: Long): Int

}
