package com.wespot.common

import kotlin.reflect.KFunction1

data class ViewedOnBoardingSheet(
    val id: Long,
    val userId: Long,
    var isViewedMessageOnBoardingSheet: Boolean = false,
    var isViewedVoteOnBoardingSheet: Boolean = false,
    var isViewedAnswerMessageOnBoardingSheet: Boolean = false,
    var isViewedPostOnBoardingSheet: Boolean = false,
) {

    companion object {
        private const val IS_MESSAGE_VIEW = "MESSAGE"
        private const val IS_VOTE_VIEW = "VOTE"
        private const val IS_ANSWER_MESSAGE_VIEW = "ANSWER_MESSAGE"
        private const val IS_POST_VIEW = "POST"

        fun createInitialState(
            userId: Long,
            existsViewedOnBoardingSheet: ViewedOnBoardingSheet?
        ): ViewedOnBoardingSheet {
            return existsViewedOnBoardingSheet ?: ViewedOnBoardingSheet(
                id = 0,
                userId = userId,
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

        if (name == IS_ANSWER_MESSAGE_VIEW) {
            isViewedAnswerMessageOnBoardingSheet = true
        }

        if (name == IS_POST_VIEW) {
            isViewedPostOnBoardingSheet = true
        }

        commited.call(this)
    }

    fun isViewed(name: String): Boolean {
        return when (name) {
            "MESSAGE" -> isViewedMessageOnBoardingSheet
            "VOTE" -> isViewedVoteOnBoardingSheet
            "ANSWER_MESSAGE" -> isViewedAnswerMessageOnBoardingSheet
            "POST" -> isViewedPostOnBoardingSheet
            else -> {
                false
            }
        }
    }

}
