package com.wespot.common

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EntityListeners
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@Embeddable
class BaseEntity(

    @CreatedDate
    @field:NotNull
    @field:Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    @field:NotNull
    @field:Column(nullable = false)
    val updatedAt: LocalDateTime

)
