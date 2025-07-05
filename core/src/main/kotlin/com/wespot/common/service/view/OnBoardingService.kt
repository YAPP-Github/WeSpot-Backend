package com.wespot.common.service.view

import com.wespot.auth.service.SecurityUtils
import com.wespot.common.dto.OnBoardingComponentRequest
import com.wespot.common.dto.OnBoardingResponse
import com.wespot.common.`in`.OnBoardingUseCase
import com.wespot.common.out.ViewedOnBoardingSheetPort
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.notification.LatestVersionType
import com.wespot.notification.port.out.LatestVersionPort
import com.wespot.user.port.out.UserPort
import com.wespot.user.port.out.UserVersionPort
import com.wespot.view.OnBoardingBottomSheetComponent
import com.wespot.view.OnBoardingCategory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OnBoardingService(
    private val userPort: UserPort,
    private val viewedOnBoardingSheetPort: ViewedOnBoardingSheetPort,
    private val userVersionPort: UserVersionPort,
    private val latestVersionPort: LatestVersionPort,
) : OnBoardingUseCase {

    @Transactional(readOnly = true)
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

        val onBoardingBottomSheetComponent =
            OnBoardingBottomSheetComponent.fromWithCategory(onBoardingCategoryNameByUpdated(userId, category))

        val firstPage = onBoardingBottomSheetComponent.onBoardingWelcomePageComponent
        val secondPage = onBoardingBottomSheetComponent.onBoardingExplanationComponent
        var pageId = 0L

        return listOfNotNull(
            if (firstPage == null) null else OnBoardingResponse.fromFirstPage(
                id = ++pageId,
                onBoardingBottomSheetComponentName = onBoardingBottomSheetComponent.name,
                onBoardingWelcomePageComponent = firstPage
            ),
            if (secondPage == null) null else OnBoardingResponse.fromSecondPage(
                id = ++pageId,
                onBoardingBottomSheetComponentName = onBoardingBottomSheetComponent.name,
                onBoardingExplanationComponent = secondPage
            )
        )
    }

    private fun onBoardingCategoryNameByUpdated(
        userId: Long,
        category: OnBoardingComponentRequest
    ): OnBoardingCategory {
        val iosLatestVersion = latestVersionPort.get(LatestVersionType.IOS)
        val androidLatestVersion = latestVersionPort.get(LatestVersionType.ANDROID)
        val userVersion = userVersionPort.findByUserId(userId)
        if (userVersion?.hasLatestVersion(
                iosLatestVersion = iosLatestVersion,
                androidLatestVersion = androidLatestVersion
            ) == true
        ) {
            return category.categoryIfUserUpdated
        }

        return category.categoryIfUserDoNotUpdate
    }

    @Transactional
    override fun viewOnBoardingSheetBy(category: OnBoardingComponentRequest) { // TODO : 일단 이 처리 없애놓으
//        val userId = SecurityUtils.getLoginUser(userPort).id
//        val isViewedOnBoardingSheet = viewedOnBoardingSheetPort.findByUserId(userId) ?: return

//        isViewedOnBoardingSheet.view(
//            category.name,
//            viewedOnBoardingSheetPort::save
//        )
    }

}
