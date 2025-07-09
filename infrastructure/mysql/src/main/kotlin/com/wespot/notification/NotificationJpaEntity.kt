package com.wespot.notification

import com.wespot.common.BaseEntity
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "notification")
class NotificationJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val userId: Long,

    @Enumerated(value = EnumType.STRING)
    val type: NotificationType,

    @field: NotNull
    val date: LocalDate,

    @field: NotNull
    val targetId: Long,

    @field: NotNull
    val title: String,

    @field: NotNull
    val body: String,

    @field: NotNull
    val isRead: Boolean,

    val readAt: LocalDateTime?,

    @field: NotNull
    val isEnabled: Boolean,

    @Embedded
    val baseEntity: BaseEntity

)
