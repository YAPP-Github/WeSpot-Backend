package com.wespot.user.restriction

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.RestrictionType
import org.springframework.http.HttpStatus
import java.time.LocalDate

data class Restriction(
    val id: Long = 0L,
    val voteRestriction: VoteRestriction = VoteRestriction.createInitialState(),
    val messageRestriction: MessageRestriction = MessageRestriction.createInitialState(),
    val communityRestriction: CommunityRestriction = CommunityRestriction(),
) {

    companion object {

        fun createInitialState() = Restriction()

    }

    fun receivedNewReport(
        restrictionCategory: RestrictionCategory,
        reportCount: Long,
    ): Restriction {
        if (restrictionCategory == RestrictionCategory.VOTE) {
            return copy(
                voteRestriction = voteRestriction.receivedNewReport(reportCount)
            )
        }

        if (restrictionCategory == RestrictionCategory.MESSAGE) {
            return copy(
                messageRestriction = messageRestriction.receivedNewReport(reportCount)
            )
        }

        return copy(
            communityRestriction = communityRestriction.receivedNewReport(reportCount)
        )
    }

    fun addRestrict(restrictionType: RestrictionType, restrictionDay: Long): Restriction {
        if (restrictionType == RestrictionType.NONE) {
            throw CustomException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ExceptionView.TOAST,
                "RestrictionType.NONE을 추가할 수 없습니다."
            )
        }

        if (restrictionType.isVoteRestriction()) {
            return copy(
                voteRestriction = VoteRestriction.of(restrictionType, restrictionDay),
            )
        }

        return copy(
            messageRestriction = MessageRestriction.of(restrictionType, restrictionDay)
        )
    }

    fun getCurrentRestrictionBasedOnTime(date: LocalDate): Restriction {
        return Restriction(
            id = id,
            voteRestriction = voteRestriction.getCurrentRestrictionBasedOnTime(date),
            messageRestriction = messageRestriction.getCurrentRestrictionBasedOnTime(date),
            communityRestriction = communityRestriction.getCurrentRestrictionBasedOnTime(date)
        )
    }

    fun isKeepRestriction(): Boolean {
        return voteRestriction.isKeepRestriction() || messageRestriction.isKeepRestriction() || communityRestriction.isKeepRestriction()
    }

    fun canNotUseVoteFeature(): Boolean {
        return voteRestriction.isKeepRestriction()
    }

    fun canNotUseMessageFeature(): Boolean {
        return messageRestriction.isKeepRestriction()
    }

    fun canNotUseCommunityFeature(): Boolean {
        return communityRestriction.isKeepRestriction()
    }

}
