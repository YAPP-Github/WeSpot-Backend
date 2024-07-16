package com.wespot.notification

import org.springframework.data.jpa.repository.JpaRepository

interface NotificationJpaRepository : JpaRepository<NotificationJpaEntity, Long> {
}
