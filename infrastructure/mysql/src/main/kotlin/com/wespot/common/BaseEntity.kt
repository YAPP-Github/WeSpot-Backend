package com.wespot.common

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    val createdAt: LocalDateTime? = null

    @LastModifiedDate
    val updatedAt: LocalDateTime? = null

}