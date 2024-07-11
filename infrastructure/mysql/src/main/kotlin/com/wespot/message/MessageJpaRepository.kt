package com.wespot.message

import org.springframework.data.jpa.repository.JpaRepository

interface MessageJpaRepository : JpaRepository<MessageJpaEntity, Long> {
}