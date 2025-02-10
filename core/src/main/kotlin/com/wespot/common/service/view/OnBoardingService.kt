package com.wespot.common.service.view

import com.wespot.auth.service.SecurityUtils
import com.wespot.common.ViewedOnBoardingSheet
import com.wespot.common.dto.OnBoardingComponentRequest
import com.wespot.common.dto.view.OnBoardingResponse
import com.wespot.common.`in`.OnBoardingUseCase
import com.wespot.common.out.ViewedOnBoardingSheetPort
import com.wespot.common.view.OnBoardingBottomSheetComponent
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.port.out.UserPort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.servlet.View

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
        val isViewedOnBoardingSheet = viewedOnBoardingSheetPort.findByUserId(userId) ?: throw CustomException(
            HttpStatus.NOT_FOUND,
            ExceptionView.TOAST,
            "해당 계정이 존재하지 않습니다."
        )
        return isViewedOnBoardingSheet.isFirstView(
            category.name,
            viewedOnBoardingSheetPort::save
        )
    }

}
