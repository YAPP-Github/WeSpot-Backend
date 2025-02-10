package com.wespot.common

import org.springframework.data.jpa.repository.JpaRepository

interface ViewedOnBoardingJpaRepository : JpaRepository<ViewedOnBoardingSheetEntity, Long> {

    fun findByUserId(userId: Long): ViewedOnBoardingSheetEntity?

}
