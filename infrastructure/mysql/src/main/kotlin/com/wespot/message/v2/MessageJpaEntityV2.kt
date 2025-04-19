package com.wespot.message.v2

import com.wespot.common.BaseEntity
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

@Entity
@Table(name = "message_v2")
class MessageJpaEntityV2(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @field: NotNull
    val content: String,
    @field: NotNull
    val senderId: Long,
    @field: NotNull
    val receiverId: Long,

    @field: NotNull
    val isReceiverRead: Boolean,
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
    val receiverDeletedAt: LocalDateTime?,

    val messageRoomId: Long?,
    @field: NotNull
    val messageRoomOwnerId: Long,
    @field: NotNull
    val isBookmarked: Boolean = false,

    @field: NotNull
    val isAnonymous: Boolean,
    val anonymousProfileId: Long?,

    ) {

}
