package com.wespot.common

import org.springframework.data.jpa.repository.JpaRepository

interface ViewedOnBoardingJpaRepository : JpaRepository<ViewedOnBoardingSheetJpaEntity, Long> {

    fun findByUserId(userId: Long): ViewedOnBoardingSheetJpaEntity?

    fun deleteByUserId(userId: Long)

}
