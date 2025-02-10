package com.wespot.common

import jakarta.persistence.*
import software.amazon.awssdk.annotations.NotNull

@Entity
@Table(name = "viewed_on_boarding_sheet", uniqueConstraints = [UniqueConstraint(columnNames = ["user_id"])])
data class ViewedOnBoardingSheetEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field: NotNull
    val userId: Long,

    val isViewedMessageOnBoardingSheet: Boolean,

    val isViewedVoteOnBoardingSheet: Boolean,

    @Embedded
    val baseEntity: BaseEntity,
) {

}
