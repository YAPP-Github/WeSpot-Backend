package com.wespot.message.service

import com.wespot.message.port.`in`.MessageUseCase
import com.wespot.message.port.out.MessageStatePort
import org.springframework.stereotype.Service

@Service
class MessageService(
    val messageStatePort: MessageStatePort
) : MessageUseCase {
}