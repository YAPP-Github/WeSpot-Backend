package com.wespot.common

import java.time.LocalDateTime

object ViewedOnBoardingSheetMapper {

    fun mapToDomainEntity(viewedOnBoardingSheetEntity: ViewedOnBoardingSheetJpaEntity): ViewedOnBoardingSheet =
        ViewedOnBoardingSheet(
            id = viewedOnBoardingSheetEntity.id,
            userId = viewedOnBoardingSheetEntity.userId,
            isViewedVoteOnBoardingSheet = viewedOnBoardingSheetEntity.isViewedVoteOnBoardingSheet,
            isViewedMessageOnBoardingSheet = viewedOnBoardingSheetEntity.isViewedMessageOnBoardingSheet,
            isViewedAnswerMessageOnBoardingSheet = viewedOnBoardingSheetEntity.isViewedAnswerMessageOnBoardingSheet
        )

    fun mapToJpaEntity(viewedOnBoardingSheet: ViewedOnBoardingSheet): ViewedOnBoardingSheetJpaEntity =
        ViewedOnBoardingSheetJpaEntity(
            id = viewedOnBoardingSheet.id,
            userId = viewedOnBoardingSheet.userId,
            isViewedVoteOnBoardingSheet = viewedOnBoardingSheet.isViewedVoteOnBoardingSheet,
            isViewedMessageOnBoardingSheet = viewedOnBoardingSheet.isViewedMessageOnBoardingSheet,
            isViewedAnswerMessageOnBoardingSheet = viewedOnBoardingSheet.isViewedAnswerMessageOnBoardingSheet,
            baseEntity = BaseEntity(LocalDateTime.now(), LocalDateTime.now())
        )

}
