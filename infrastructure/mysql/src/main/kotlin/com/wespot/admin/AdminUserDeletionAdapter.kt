package com.wespot.admin

import com.wespot.admin.port.out.AdminUserDeletionPort
import com.wespot.auth.RefreshTokenJpaRepository
import com.wespot.comment.repository.PostCommentJpaRepository
import com.wespot.comment.repository.PostCommentLikeJpaRepository
import com.wespot.comment.repository.PostCommentReportJpaRepository
import com.wespot.comment.repository.PostCommentReportReasonJpaRepository
import com.wespot.common.ViewedOnBoardingJpaRepository
import com.wespot.message.v1.MessageJpaRepository
import com.wespot.message.v2.MessageV2JpaRepository
import com.wespot.notification.NotificationJpaRepository
import com.wespot.post.repository.PostBlockJpaRepository
import com.wespot.post.repository.PostJpaRepository
import com.wespot.post.repository.PostLikeJpaRepository
import com.wespot.post.repository.PostNotificationJpaRepository
import com.wespot.post.repository.PostProfileJpaRepository
import com.wespot.post.repository.PostReportJpaRepository
import com.wespot.post.repository.PostReportReasonJpaRepository
import com.wespot.post.repository.PostScrapJpaRepository
import com.wespot.report.ReportJpaRepository
import com.wespot.user.repository.AnonymousProfileJpaRepository
import com.wespot.user.repository.BlockedUserJpaRepository
import com.wespot.user.repository.FCMJpaRepository
import com.wespot.user.repository.ProfileJpaRepository
import com.wespot.user.repository.RestrictionJpaRepository
import com.wespot.user.repository.UsedAnswerMessageJpaRepository
import com.wespot.user.repository.UserConsentJpaRepository
import com.wespot.user.repository.UserJpaRepository
import com.wespot.user.repository.UserPolicyAgreementJpaRepository
import com.wespot.user.repository.UserVersionJpaRepository
import com.wespot.vote.BallotJpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class AdminUserDeletionAdapter(
    private val userJpaRepository: UserJpaRepository,
    private val profileJpaRepository: ProfileJpaRepository,
    private val fcmJpaRepository: FCMJpaRepository,
    private val userConsentJpaRepository: UserConsentJpaRepository,
    private val restrictionJpaRepository: RestrictionJpaRepository,
    private val refreshTokenJpaRepository: RefreshTokenJpaRepository,
    private val userPolicyAgreementJpaRepository: UserPolicyAgreementJpaRepository,
    private val anonymousProfileJpaRepository: AnonymousProfileJpaRepository,
    private val ballotJpaRepository: BallotJpaRepository,
    private val blockedUserJpaRepository: BlockedUserJpaRepository,
    private val postCommentJpaRepository: PostCommentJpaRepository,
    private val postCommentLikeJpaRepository: PostCommentLikeJpaRepository,
    private val postCommentReportJpaRepository: PostCommentReportJpaRepository,
    private val postCommentReportReasonJpaRepository: PostCommentReportReasonJpaRepository,
    private val messageJpaRepository: MessageJpaRepository,
    private val messageV2JpaRepository: MessageV2JpaRepository,
    private val notificationJpaRepository: NotificationJpaRepository,
    private val postJpaRepository: PostJpaRepository,
    private val postBlockJpaRepository: PostBlockJpaRepository,
    private val postLikeJpaRepository: PostLikeJpaRepository,
    private val postNotificationJpaRepository: PostNotificationJpaRepository,
    private val postProfileJpaRepository: PostProfileJpaRepository,
    private val postReportJpaRepository: PostReportJpaRepository,
    private val postReportReasonJpaRepository: PostReportReasonJpaRepository,
    private val postScrapJpaRepository: PostScrapJpaRepository,
    private val reportJpaRepository: ReportJpaRepository,
    private val usedAnswerMessageJpaRepository: UsedAnswerMessageJpaRepository,
    private val userVersionJpaRepository: UserVersionJpaRepository,
    private val viewedOnBoardingJpaRepository: ViewedOnBoardingJpaRepository,
) : AdminUserDeletionPort {

    override fun reassignPostUserId(fromUserId: Long, toUserId: Long) {
        postJpaRepository.reassignUserId(fromUserId, toUserId)
    }

    override fun reassignPostCommentUserId(fromUserId: Long, toUserId: Long) {
        postCommentJpaRepository.reassignUserId(fromUserId, toUserId)
    }

    override fun deleteRefreshTokenByUserId(userId: Long) {
        refreshTokenJpaRepository.deleteByUserId(userId)
    }

    override fun deleteUserPolicyAgreementByUserId(userId: Long) {
        userPolicyAgreementJpaRepository.deleteByUserId(userId)
    }

    override fun deleteAnonymousProfilesByUserId(userId: Long) {
        anonymousProfileJpaRepository.deleteByOwnerIdOrReceiverId(userId, userId)
    }

    override fun deleteBallotsByUserId(userId: Long) {
        ballotJpaRepository.deleteByReceiverIdOrSenderId(userId, userId)
    }

    override fun deleteBlockedUsersByUserId(userId: Long) {
        blockedUserJpaRepository.deleteByBlockerIdOrBlockedId(userId, userId)
    }

    override fun deletePostCommentLikesByUserId(userId: Long) {
        postCommentLikeJpaRepository.deleteByUserId(userId)
    }

    override fun deletePostCommentReportsByUserId(userId: Long) {
        val reportIds = postCommentReportJpaRepository.findAllByUserId(userId).map { it.id }
        if (reportIds.isNotEmpty()) {
            postCommentReportReasonJpaRepository.deleteByPostCommentReportIdIn(reportIds)
        }
        postCommentReportJpaRepository.deleteByUserId(userId)
    }

    override fun deleteMessagesV1ByUserId(userId: Long) {
        messageJpaRepository.deleteBySenderIdOrReceiverId(userId, userId)
    }

    override fun deleteMessagesV2ByUserId(userId: Long) {
        messageV2JpaRepository.deleteBySenderIdOrReceiverId(userId, userId)
    }

    override fun deleteNotificationsByUserId(userId: Long) {
        notificationJpaRepository.deleteByUserId(userId)
    }

    override fun deletePostBlocksByUserId(userId: Long) {
        postBlockJpaRepository.deleteByUserId(userId)
    }

    override fun deletePostLikesByUserId(userId: Long) {
        postLikeJpaRepository.deleteByUserId(userId)
    }

    override fun deletePostNotificationsByUserId(userId: Long) {
        postNotificationJpaRepository.deleteByUserId(userId)
    }

    override fun deletePostProfileByUserId(userId: Long) {
        postProfileJpaRepository.deleteByUserId(userId)
    }

    override fun deletePostReportsByUserId(userId: Long) {
        val reportIds = postReportJpaRepository.findAllByUserId(userId).map { it.id }
        if (reportIds.isNotEmpty()) {
            postReportReasonJpaRepository.deleteByPostReportIdIn(reportIds)
        }
        postReportJpaRepository.deleteByUserId(userId)
    }

    override fun deletePostScrapsByUserId(userId: Long) {
        postScrapJpaRepository.deleteByUserId(userId)
    }

    override fun deleteReportsByUserId(userId: Long) {
        reportJpaRepository.deleteBySenderIdOrReceiverId(userId, userId)
    }

    override fun deleteUsedAnswerMessageByUserId(userId: Long) {
        usedAnswerMessageJpaRepository.deleteByUserId(userId)
    }

    override fun deleteUserVersionByUserId(userId: Long) {
        userVersionJpaRepository.deleteByUserId(userId)
    }

    override fun deleteViewedOnBoardingSheetByUserId(userId: Long) {
        viewedOnBoardingJpaRepository.deleteByUserId(userId)
    }

    override fun deleteUserById(userId: Long) {
        val userEntity = userJpaRepository.findById(userId).orElse(null) ?: return

        val profileId = userEntity.profile.id
        val fcmId = userEntity.fcm.id
        val userConsentId = userEntity.userConsent.id
        val restrictionId = userEntity.restriction.id

        userJpaRepository.deleteById(userId)
        userJpaRepository.flush()

        profileJpaRepository.deleteById(profileId)
        fcmJpaRepository.deleteById(fcmId)
        userConsentJpaRepository.deleteById(userConsentId)
        restrictionJpaRepository.deleteById(restrictionId)
    }

}
