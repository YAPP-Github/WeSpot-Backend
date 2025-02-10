package com.wespot.common

import java.time.LocalDateTime

object ViewedOnBoardingSheetMapper {

    fun mapToDomainEntity(viewedOnBoardingSheetEntity: ViewedOnBoardingSheetEntity): ViewedOnBoardingSheet =
        ViewedOnBoardingSheet(
            id = viewedOnBoardingSheetEntity.id,
            userId = viewedOnBoardingSheetEntity.userId,
            isViewedVoteOnBoardingSheet = viewedOnBoardingSheetEntity.isViewedVoteOnBoardingSheet,
            isViewedMessageOnBoardingSheet = viewedOnBoardingSheetEntity.isViewedMessageOnBoardingSheet
        )

    fun mapToJpaEntity(viewedOnBoardingSheet: ViewedOnBoardingSheet): ViewedOnBoardingSheetEntity =
        ViewedOnBoardingSheetEntity(
            id = viewedOnBoardingSheet.id,
            userId = viewedOnBoardingSheet.userId,
            isViewedVoteOnBoardingSheet = viewedOnBoardingSheet.isViewedVoteOnBoardingSheet,
            isViewedMessageOnBoardingSheet = viewedOnBoardingSheet.isViewedMessageOnBoardingSheet,
            baseEntity = BaseEntity(LocalDateTime.now(), LocalDateTime.now())
        )

}
