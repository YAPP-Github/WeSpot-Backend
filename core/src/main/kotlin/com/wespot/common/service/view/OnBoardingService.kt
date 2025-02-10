package com.wespot.common.service.view

import com.wespot.auth.service.SecurityUtils
import com.wespot.common.dto.OnBoardingComponentRequest
import com.wespot.common.dto.view.OnBoardingResponse
import com.wespot.common.`in`.OnBoardingUseCase
import com.wespot.common.out.ViewedOnBoardingSheetPort
import com.wespot.common.view.OnBoardingBottomSheetComponent
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OnBoardingService(
    private val userPort: UserPort,
    private val viewedOnBoardingSheetPort: ViewedOnBoardingSheetPort,
) : OnBoardingUseCase {

    override fun getOnBoardingComponents(category: OnBoardingComponentRequest): OnBoardingResponse {
        val onBoardingBottomSheetComponent = OnBoardingBottomSheetComponent.fromWithCategory(category.name)

        return OnBoardingResponse.from(onBoardingBottomSheetComponent)
    }

    @Transactional
    override fun isViewedOnBoardingSheetBy(category: OnBoardingComponentRequest): Boolean {
        val userId = SecurityUtils.getLoginUser(userPort).id
        val isViewedOnBoardingSheet = viewedOnBoardingSheetPort.findByUserId(userId) ?: return true
        return isViewedOnBoardingSheet.isAlreadyViewed(
            category.name,
            viewedOnBoardingSheetPort::save
        )
    }

}
