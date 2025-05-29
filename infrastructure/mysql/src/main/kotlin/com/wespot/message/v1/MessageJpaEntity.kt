package com.wespot.message.v1

import com.wespot.common.BaseEntity
import com.wespot.message.MessageType
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

    @field: NotNull
    val isAnonymous: Boolean,

    @field: NotNull
    val isReceiverRead: Boolean,

    @Enumerated(EnumType.STRING)
    val messageType: MessageType,

    @field: NotNull
    val isSend: Boolean,

    val sendAt: LocalDateTime?,

    val receivedAt: LocalDateTime?,

    val readAt: LocalDateTime?,

    @field: NotNull
    val isReported: Boolean,

    @Embedded
    val baseEntity: BaseEntity,

    @field: NotNull
    val isSenderDeleted: Boolean,

    val senderDeletedAt: LocalDateTime?,

    @field: NotNull
    val isReceiverDeleted: Boolean,

    val receiverDeletedAt: LocalDateTime?

)
