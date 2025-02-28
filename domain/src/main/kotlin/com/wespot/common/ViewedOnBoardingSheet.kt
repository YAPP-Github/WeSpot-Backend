package com.wespot.common

import kotlin.reflect.KFunction1

data class ViewedOnBoardingSheet(
    val id: Long,
    val userId: Long,
    var isViewedMessageOnBoardingSheet: Boolean,
    var isViewedVoteOnBoardingSheet: Boolean,
) {

    companion object {
        private const val IS_MESSAGE_VIEW = "MESSAGE"
        private const val IS_VOTE_VIEW = "VOTE"

        fun createInitialState(
            userId: Long,
            existsViewedOnBoardingSheet: ViewedOnBoardingSheet?
        ): ViewedOnBoardingSheet {
            return existsViewedOnBoardingSheet ?: ViewedOnBoardingSheet(
                id = 0,
                userId = userId,
                isViewedMessageOnBoardingSheet = false,
                isViewedVoteOnBoardingSheet = false
            )
        }
    }

    fun view(name: String, commited: KFunction1<ViewedOnBoardingSheet, ViewedOnBoardingSheet>) {
        if (name == IS_MESSAGE_VIEW) {
            isViewedMessageOnBoardingSheet = true
        }

        if (name == IS_VOTE_VIEW) {
            isViewedVoteOnBoardingSheet = true
        }

        commited.call(this)
    }

}
