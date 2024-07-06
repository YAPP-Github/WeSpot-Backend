package com.wespot.message

import org.springframework.stereotype.Repository

@Repository
class MessagePersistenceAdapter(
    val messageJpaRepository: MessageJpaRepository
) {
}