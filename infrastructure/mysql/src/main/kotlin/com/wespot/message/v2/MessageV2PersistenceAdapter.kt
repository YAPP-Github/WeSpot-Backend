package com.wespot.message.v2

import com.wespot.message.MessageV2
import com.wespot.message.port.out.MessageV2Port
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Repository
class MessageV2PersistenceAdapter(
    private val messageV2JpaRepository: MessageV2JpaRepository,
) : MessageV2Port {

    override fun save(messageV2: MessageV2): MessageV2 {
        val sender = messageV2.sender
        val receiver = messageV2.receiver
        val anonymousProfile = messageV2.anonymousProfile
        val messageV2JpaEntity = MessageV2Mapper.mapToJpaEntity(messageV2)
        val savedMessageV2 = messageV2JpaRepository.save(messageV2JpaEntity)

        return MessageV2Mapper.mapToDomainEntity(savedMessageV2, sender, receiver, anonymousProfile)
    }

    override fun countTodaySendMessages(userId: Long): Int {
        val today = LocalDate.now()
        val startOfDay = today.atStartOfDay()
        val endOfDay = LocalDateTime.of(today, LocalTime.MAX)

        return messageV2JpaRepository.countBySenderIdAndBaseEntityCreatedAtBetween(userId, startOfDay, endOfDay)
    }

}
