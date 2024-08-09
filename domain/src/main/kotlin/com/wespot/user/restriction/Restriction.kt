package com.wespot.user.restriction

import com.wespot.user.RestrictionType
import java.time.LocalDate

data class Restriction(
    val voteRestriction: VoteRestriction,
    val messageRestriction: MessageRestriction
) {

    companion object {

        fun createInitialState() =
            Restriction(
                voteRestriction = VoteRestriction.createInitialState(),
                messageRestriction = MessageRestriction.createInitialState()
            )

    }

    fun addRestrict(restrictionType: RestrictionType, restrictionDay: Long): Restriction {
        validate(restrictionType)

        if (restrictionType.isVoteRestriction()) {
            return Restriction(
                voteRestriction = VoteRestriction.of(restrictionType, restrictionDay),
                messageRestriction = messageRestriction
            )
        }

        return Restriction(
            voteRestriction = voteRestriction,
            messageRestriction = MessageRestriction.of(restrictionType, restrictionDay)
        )
    }

    private fun validate(restrictionType: RestrictionType) {
        require(restrictionType.isVoteRestriction() || restrictionType.isMessageRestriction()) { "RestrictionType.NONE을 추가할 수 없습니다." }
    }


    fun getCurrentRestrictionBasedOnTime(date: LocalDate): Restriction {
        return Restriction(
            voteRestriction = voteRestriction.getCurrentRestrictionBasedOnTime(date),
            messageRestriction = messageRestriction.getCurrentRestrictionBasedOnTime(date)
        )
    }

    fun isKeepRestriction(): Boolean {
        return voteRestriction.isKeepRestriction() && messageRestriction.isKeepRestriction()
    }

}
