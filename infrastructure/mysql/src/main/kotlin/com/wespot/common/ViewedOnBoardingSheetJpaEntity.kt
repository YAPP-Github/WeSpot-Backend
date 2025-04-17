package com.wespot.common

import jakarta.persistence.*
import software.amazon.awssdk.annotations.NotNull

@Entity
@Table(name = "viewed_on_boarding_sheet", uniqueConstraints = [UniqueConstraint(columnNames = ["user_id"])])
data class ViewedOnBoardingSheetJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val userId: Long,

    @field: NotNull
    val isViewedMessageOnBoardingSheet: Boolean,

    @field: NotNull
    val isViewedVoteOnBoardingSheet: Boolean,

    @field: NotNull
    val isViewedAnswerMessageOnBoardingSheet: Boolean,

    @Embedded
    val baseEntity: BaseEntity

) {

}
