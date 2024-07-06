package com.wespot.notification

import com.wespot.common.BaseEntity
import com.wespot.user.UserJpaEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "notification")
class NotificationJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne
    @JoinColumn(name = "sender_id")
    val user: UserJpaEntity,

    @Enumerated(value = EnumType.STRING)
    val type: NotificationType,

    val targetId: Long,

    val content: String,

    val isRead: Boolean,

    val readAt: LocalDateTime,

    val isEnabled: Boolean,

    @Embedded
    val baseEntity: BaseEntity

)
