package com.wespot.common.out

import com.wespot.common.ViewedOnBoardingSheet

interface ViewedOnBoardingSheetPort {

    fun findByUserId(userId: Long): ViewedOnBoardingSheet?

    fun save(viewedOnBoardingSheet: ViewedOnBoardingSheet): ViewedOnBoardingSheet

}
