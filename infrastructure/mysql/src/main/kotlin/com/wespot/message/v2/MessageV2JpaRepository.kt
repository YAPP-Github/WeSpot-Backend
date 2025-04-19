package com.wespot.message.v2

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface MessageV2JpaRepository : JpaRepository<MessageJpaEntityV2, Long> {

    fun countBySenderIdAndBaseEntityCreatedAtBetween(senderId: Long, from: LocalDateTime, to: LocalDateTime): Int

}
