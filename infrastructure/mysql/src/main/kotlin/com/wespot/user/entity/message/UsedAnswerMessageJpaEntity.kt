package com.wespot.user.entity.message

import com.wespot.common.BaseEntity
import jakarta.persistence.*


@Entity
@Table(name = "used_answer_message")
class UsedAnswerMessageJpaEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val userId: Long,

    val isUsedAnswerMessageFeature: Boolean = false,

    @Embedded
    val baseEntity: BaseEntity

) {
}
