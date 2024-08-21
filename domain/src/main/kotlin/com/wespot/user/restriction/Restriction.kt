package com.wespot.user.restriction

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.RestrictionType
import org.springframework.http.HttpStatus
import java.time.LocalDate

data class Restriction(
    val id: Long,
    val voteRestriction: VoteRestriction,
    val messageRestriction: MessageRestriction
) {

    companion object {

        fun createInitialState() =
            Restriction(
                id = 0,
                voteRestriction = VoteRestriction.createInitialState(),
                messageRestriction = MessageRestriction.createInitialState()
            )

    }

    fun addRestrict(restrictionType: RestrictionType, restrictionDay: Long): Restriction {
        validate(restrictionType)

        if (restrictionType.isVoteRestriction()) {
            return Restriction(
                id = id,
                voteRestriction = VoteRestriction.of(restrictionType, restrictionDay),
                messageRestriction = messageRestriction
            )
        }

        return Restriction(
            id = id,
            voteRestriction = voteRestriction,
            messageRestriction = MessageRestriction.of(restrictionType, restrictionDay)
        )
    }

    private fun validate(restrictionType: RestrictionType) {
        require(restrictionType.isVoteRestriction() || restrictionType.isMessageRestriction()) {
            throw CustomException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ExceptionView.TOAST,
                "RestrictionType.NONE을 추가할 수 없습니다."
            )
        }
    }


    fun getCurrentRestrictionBasedOnTime(date: LocalDate): Restriction {
        return Restriction(
            id = id,
            voteRestriction = voteRestriction.getCurrentRestrictionBasedOnTime(date),
            messageRestriction = messageRestriction.getCurrentRestrictionBasedOnTime(date)
        )
    }

    fun isKeepRestriction(): Boolean {
        return voteRestriction.isKeepRestriction() || messageRestriction.isKeepRestriction()
    }

}
