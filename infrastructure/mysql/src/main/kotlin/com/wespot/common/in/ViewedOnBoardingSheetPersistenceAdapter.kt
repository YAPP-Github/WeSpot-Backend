package com.wespot.common.`in`

import com.wespot.common.ViewedOnBoardingJpaRepository
import com.wespot.common.ViewedOnBoardingSheet
import com.wespot.common.ViewedOnBoardingSheetMapper
import com.wespot.common.out.ViewedOnBoardingSheetPort
import org.springframework.stereotype.Repository

@Repository
class ViewedOnBoardingSheetPersistenceAdapter(
    private val viewedOnBoardingJpaRepository: ViewedOnBoardingJpaRepository
) : ViewedOnBoardingSheetPort {

    override fun findByUserId(userId: Long): ViewedOnBoardingSheet? {
        return viewedOnBoardingJpaRepository.findByUserId(userId)
            ?.let { ViewedOnBoardingSheetMapper.mapToDomainEntity(it) }
    }

    override fun save(viewedOnBoardingSheet: ViewedOnBoardingSheet): ViewedOnBoardingSheet {
        val entity = ViewedOnBoardingSheetMapper.mapToJpaEntity(viewedOnBoardingSheet)

        return viewedOnBoardingJpaRepository.save(entity)
            .let { ViewedOnBoardingSheetMapper.mapToDomainEntity(it) }
    }

}
