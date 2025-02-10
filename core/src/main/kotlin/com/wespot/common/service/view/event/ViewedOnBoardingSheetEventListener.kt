package com.wespot.common.service.view.event

import com.wespot.common.ViewedOnBoardingSheet
import com.wespot.common.out.ViewedOnBoardingSheetPort
import com.wespot.user.event.CreatedViewedOnBoardingSheetEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class ViewedOnBoardingSheetEventListener(
    private val viewedOnBoardingSheetPort: ViewedOnBoardingSheetPort,
) {

    @EventListener
    fun viewedOnBoardingSheet(createdViewedOnBoardingSheetEvent: CreatedViewedOnBoardingSheetEvent) {
        val existsOnBoardingSheet = viewedOnBoardingSheetPort.findByUserId(createdViewedOnBoardingSheetEvent.userId)
        val viewedOnBoardingSheet = ViewedOnBoardingSheet.createInitialState(
            createdViewedOnBoardingSheetEvent.userId,
            existsOnBoardingSheet
        )
        viewedOnBoardingSheetPort.save(viewedOnBoardingSheet)
    }

}
