package com.wespot.message.service

import com.wespot.message.port.`in`.MessageUseCase
import com.wespot.message.port.out.MessagePort

class MessageService(
    private val messagePort: MessagePort
) : MessageUseCase {
}
