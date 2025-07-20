package com.wespot.common

import java.time.LocalDateTime

object ViewedOnBoardingSheetMapper {

    fun mapToDomainEntity(viewedOnBoardingSheetEntity: ViewedOnBoardingSheetJpaEntity): ViewedOnBoardingSheet =
        ViewedOnBoardingSheet(
            id = viewedOnBoardingSheetEntity.id,
            userId = viewedOnBoardingSheetEntity.userId,
            isViewedVoteOnBoardingSheet = viewedOnBoardingSheetEntity.isViewedVoteOnBoardingSheet,
            isViewedMessageOnBoardingSheet = viewedOnBoardingSheetEntity.isViewedMessageOnBoardingSheet,
            isViewedPostOnBoardingSheet = viewedOnBoardingSheetEntity.isViewedPostOnBoardingSheet,
            isViewedAnswerMessageOnBoardingSheet = viewedOnBoardingSheetEntity.isViewedAnswerMessageOnBoardingSheet
        )

    fun mapToJpaEntity(viewedOnBoardingSheet: ViewedOnBoardingSheet): ViewedOnBoardingSheetJpaEntity =
        ViewedOnBoardingSheetJpaEntity(
            id = viewedOnBoardingSheet.id,
            userId = viewedOnBoardingSheet.userId,
            isViewedVoteOnBoardingSheet = viewedOnBoardingSheet.isViewedVoteOnBoardingSheet,
            isViewedMessageOnBoardingSheet = viewedOnBoardingSheet.isViewedMessageOnBoardingSheet,
            isViewedAnswerMessageOnBoardingSheet = viewedOnBoardingSheet.isViewedAnswerMessageOnBoardingSheet,
            isViewedPostOnBoardingSheet = viewedOnBoardingSheet.isViewedPostOnBoardingSheet,
            baseEntity = BaseEntity(LocalDateTime.now(), LocalDateTime.now())
        )

}
