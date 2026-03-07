package com.wespot.admin.service

import com.wespot.admin.port.`in`.AdminUserDeletionUseCase
import com.wespot.admin.port.out.AdminUserDeletionPort
import com.wespot.user.port.out.UserPort
import com.wespot.user.service.UserFinder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AdminUserDeletionService(
    private val userPort: UserPort,
    private val adminUserDeletionPort: AdminUserDeletionPort,
) : AdminUserDeletionUseCase {

    override fun deleteUser(targetUserId: Long) {
        UserFinder.findUserById(targetUserId, userPort)
        val replacementUser = userPort.findByName("김재연") ?: throw IllegalArgumentException("대체 유저를 찾을 수 없습니다.")

        // 1. post, post_comment 소유권 대체 유저로 이전
        adminUserDeletionPort.reassignPostUserId(targetUserId, replacementUser.id)
        adminUserDeletionPort.reassignPostCommentUserId(targetUserId, replacementUser.id)

        // 2. users 테이블을 FK로 참조하는 테이블 먼저 삭제 (제약 조건 순서)
        adminUserDeletionPort.deleteRefreshTokenByUserId(targetUserId)
        adminUserDeletionPort.deleteUserPolicyAgreementByUserId(targetUserId)

        // 3. 유저 관련 데이터 삭제
        adminUserDeletionPort.deleteAnonymousProfilesByUserId(targetUserId)
        adminUserDeletionPort.deleteBallotsByUserId(targetUserId)
        adminUserDeletionPort.deleteBlockedUsersByUserId(targetUserId)
        adminUserDeletionPort.deletePostCommentReportsByUserId(targetUserId)
        adminUserDeletionPort.deletePostCommentLikesByUserId(targetUserId)
        adminUserDeletionPort.deleteMessagesV1ByUserId(targetUserId)
        adminUserDeletionPort.deleteMessagesV2ByUserId(targetUserId)
        adminUserDeletionPort.deleteNotificationsByUserId(targetUserId)
        adminUserDeletionPort.deletePostBlocksByUserId(targetUserId)
        adminUserDeletionPort.deletePostLikesByUserId(targetUserId)
        adminUserDeletionPort.deletePostNotificationsByUserId(targetUserId)
        adminUserDeletionPort.deletePostProfileByUserId(targetUserId)
        adminUserDeletionPort.deletePostReportsByUserId(targetUserId)
        adminUserDeletionPort.deletePostScrapsByUserId(targetUserId)
        adminUserDeletionPort.deleteReportsByUserId(targetUserId)
        adminUserDeletionPort.deleteUsedAnswerMessageByUserId(targetUserId)
        adminUserDeletionPort.deleteUserVersionByUserId(targetUserId)
        adminUserDeletionPort.deleteViewedOnBoardingSheetByUserId(targetUserId)

        // 4. 유저 삭제 (profile, fcm, userConsent, restriction은 cascade로 처리)
        adminUserDeletionPort.deleteUserById(targetUserId)
    }

}
