package com.wespot.admin.port.out

interface AdminUserDeletionPort {

    fun reassignPostUserId(fromUserId: Long, toUserId: Long)

    fun reassignPostCommentUserId(fromUserId: Long, toUserId: Long)

    fun deleteRefreshTokenByUserId(userId: Long)

    fun deleteUserPolicyAgreementByUserId(userId: Long)

    fun deleteAnonymousProfilesByUserId(userId: Long)

    fun deleteBallotsByUserId(userId: Long)

    fun deleteBlockedUsersByUserId(userId: Long)

    fun deletePostCommentLikesByUserId(userId: Long)

    fun deletePostCommentReportsByUserId(userId: Long)

    fun deleteMessagesV1ByUserId(userId: Long)

    fun deleteMessagesV2ByUserId(userId: Long)

    fun deleteNotificationsByUserId(userId: Long)

    fun deletePostBlocksByUserId(userId: Long)

    fun deletePostLikesByUserId(userId: Long)

    fun deletePostNotificationsByUserId(userId: Long)

    fun deletePostProfileByUserId(userId: Long)

    fun deletePostReportsByUserId(userId: Long)

    fun deletePostScrapsByUserId(userId: Long)

    fun deleteReportsByUserId(userId: Long)

    fun deleteUsedAnswerMessageByUserId(userId: Long)

    fun deleteUserVersionByUserId(userId: Long)

    fun deleteViewedOnBoardingSheetByUserId(userId: Long)

    fun deleteUserById(userId: Long)

}
