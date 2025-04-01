package com.wespot.common.service.view

import com.wespot.auth.service.SecurityUtils
import com.wespot.common.dto.OnBoardingComponentRequest
import com.wespot.common.dto.OnBoardingResponse
import com.wespot.common.`in`.OnBoardingUseCase
import com.wespot.common.out.ViewedOnBoardingSheetPort
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.port.out.UserPort
import com.wespot.view.OnBoardingBottomSheetComponent
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OnBoardingService(
    private val userPort: UserPort,
    private val viewedOnBoardingSheetPort: ViewedOnBoardingSheetPort,
) : OnBoardingUseCase {

    override fun getOnBoardingComponents(category: OnBoardingComponentRequest): List<OnBoardingResponse> {
        val userId = SecurityUtils.getLoginUser(userPort).id
        val viewedOnBoardingSheet = viewedOnBoardingSheetPort.findByUserId(userId) ?: throw CustomException(
            HttpStatus.NOT_FOUND,
            ExceptionView.TOAST,
            "해당 계정이 존재하지 않습니다."
        )

        if (viewedOnBoardingSheet.isViewed(category.name)) {
            return listOf()
        }

        val onBoardingBottomSheetComponent = OnBoardingBottomSheetComponent.fromWithCategory(category.name)

        return listOf(
            OnBoardingResponse.fromFirstPage(onBoardingBottomSheetComponent),
            OnBoardingResponse.fromSecondPage(onBoardingBottomSheetComponent)
        )
    }

    @Transactional
    override fun viewOnBoardingSheetBy(category: OnBoardingComponentRequest) {
        val userId = SecurityUtils.getLoginUser(userPort).id
        val isViewedOnBoardingSheet = viewedOnBoardingSheetPort.findByUserId(userId) ?: return

        isViewedOnBoardingSheet.view(
            category.name,
            viewedOnBoardingSheetPort::save
        )
    }

}
