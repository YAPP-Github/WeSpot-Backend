package com.wespot.message

import com.wespot.common.BaseEntity
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

@Entity
@Table(name = "message")
class MessageJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val content: String,

    @field: NotNull
    val senderId: Long,

    @field: NotNull
    val receiverId: Long,

    val isReceiverRead: Boolean,

    val readAt: LocalDateTime,

    @field: NotNull
    val isSent: Boolean,

    val sentAt: LocalDateTime,

    val receivedAt: LocalDateTime,

    @Embedded
    val baseEntity: BaseEntity

)
