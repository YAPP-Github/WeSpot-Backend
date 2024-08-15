package com.wespot.user.repository

import com.wespot.user.entity.FCMJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FCMJpaRepository :JpaRepository<FCMJpaEntity, Long> {

}
