package com.wespot.common.dto

import com.wespot.view.OnBoardingCategory

enum class OnBoardingComponentRequest(
    val categoryIfUserDoNotUpdate: OnBoardingCategory,
    val categoryIfUserUpdated: OnBoardingCategory
) {

    MESSAGE(OnBoardingCategory.MESSAGE, OnBoardingCategory.ANSWER_MESSAGE),
    VOTE(OnBoardingCategory.VOTE, OnBoardingCategory.VOTE),
    POST(OnBoardingCategory.POST, OnBoardingCategory.POST)
    ;

}
