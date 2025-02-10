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

    fun isFirstView(name: String, commited: KFunction1<ViewedOnBoardingSheet, ViewedOnBoardingSheet>): Boolean {
        if (name == IS_MESSAGE_VIEW) {
            return inCaseMessage(commited)
        }

        return inCaseVote(commited)
    }

    private fun ViewedOnBoardingSheet.inCaseMessage(commited: KFunction1<ViewedOnBoardingSheet, ViewedOnBoardingSheet>): Boolean {
        if (!isViewedMessageOnBoardingSheet) {
            isViewedMessageOnBoardingSheet = true
            commited.call(this)
        }
        return isViewedMessageOnBoardingSheet
    }

    private fun ViewedOnBoardingSheet.inCaseVote(commited: KFunction1<ViewedOnBoardingSheet, ViewedOnBoardingSheet>): Boolean {
        if (!isViewedVoteOnBoardingSheet) {
            isViewedVoteOnBoardingSheet = true
            commited.call(this)
        }
        return isViewedVoteOnBoardingSheet
    }

}
