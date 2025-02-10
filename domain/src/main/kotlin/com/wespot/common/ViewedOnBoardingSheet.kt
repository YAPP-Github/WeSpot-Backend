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

    fun isAlreadyViewed(name: String, commited: KFunction1<ViewedOnBoardingSheet, ViewedOnBoardingSheet>): Boolean {
        if (name == IS_MESSAGE_VIEW) {
            return isAlreadyViewedInCaseMessage(commited)
        }

        return isAlreadyViewedInCaseVote(commited)
    }

    private fun isAlreadyViewedInCaseMessage(commited: KFunction1<ViewedOnBoardingSheet, ViewedOnBoardingSheet>): Boolean {
        if (isViewedMessageOnBoardingSheet) {
            return true
        }

        isViewedMessageOnBoardingSheet = true
        commited.call(this)
        return false
    }

    private fun isAlreadyViewedInCaseVote(commited: KFunction1<ViewedOnBoardingSheet, ViewedOnBoardingSheet>): Boolean {
        if (isViewedVoteOnBoardingSheet) {
            return true
        }
        isViewedVoteOnBoardingSheet = true
        commited.call(this)
        return false
    }

}
