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
    val senderName: String,

    @field: NotNull
    val receiverId: Long,

    val isReceiverRead: Boolean?,

    @Enumerated(EnumType.STRING)
    val messageType: MessageType,

    @field: NotNull
    val isSend: Boolean,

    val sendAt: LocalDateTime?,

    val receivedAt: LocalDateTime?,

    val readAt: LocalDateTime?,

    @Embedded
    val baseEntity: BaseEntity,

    @field: NotNull
    val isDeleted: Boolean?,

    val deletedAt: LocalDateTime?

)
