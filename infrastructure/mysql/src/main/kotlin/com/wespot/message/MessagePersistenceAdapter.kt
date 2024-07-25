package com.wespot.message

import com.wespot.message.port.out.MessagePort
import org.springframework.stereotype.Repository

@Repository
class MessagePersistenceAdapter(
    val messageJpaRepository: MessageJpaRepository
) : MessagePort {

    override fun deleteById(id: Long) {
        messageJpaRepository.deleteById(id)
    }

    override fun existsById(id: Long): Boolean {
        return messageJpaRepository.existsById(id)
    }

}
