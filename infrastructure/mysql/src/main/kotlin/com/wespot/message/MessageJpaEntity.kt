package com.wespot.message

import com.wespot.common.BaseEntity
import com.wespot.user.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "message")
class MessageJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val content: String,

    @ManyToOne
    @JoinColumn(name = "sender_id")
    val sender: User,

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    val receiver: User,

    val isReceiverRead: Boolean,

    val readAt: LocalDateTime,

    val isSent: Boolean,

    val sentAt: LocalDateTime,

    val receivedAt: LocalDateTime,

    @Embedded
    val baseEntity: BaseEntity,
) {
}
