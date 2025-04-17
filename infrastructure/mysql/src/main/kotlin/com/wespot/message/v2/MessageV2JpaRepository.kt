package com.wespot.message.v2

import org.springframework.data.jpa.repository.JpaRepository

interface MessageV2JpaRepository : JpaRepository<MessageJpaEntityV2, Long> {
}
