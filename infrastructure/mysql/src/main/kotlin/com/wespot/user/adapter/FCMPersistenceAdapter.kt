package com.wespot.user.adapter

import com.wespot.user.FCM
import com.wespot.user.mapper.FCMMapper
import com.wespot.user.port.out.FCMPort
import com.wespot.user.repository.FCMJpaRepository
import org.springframework.stereotype.Repository

@Repository
class FCMPersistenceAdapter(
    private val fcmJpaRepository: FCMJpaRepository
) : FCMPort {

    override fun save(fcm: FCM): FCM {
        val savedFCMJpaEntity = fcmJpaRepository.save(FCMMapper.mapToJpaEntity(fcm))

        return FCMMapper.mapToDomainEntity(savedFCMJpaEntity)
    }

}
