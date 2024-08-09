package com.wespot.user.fixture

import com.wespot.user.RestrictionType
import com.wespot.user.restriction.Restriction

object RestrictionFixture {

    fun createFirstMessageUsageRestriction(): Restriction {
        val initialRestriction=Restriction.createInitialState()

        return initialRestriction.changeRestrict(RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT, 30L)
    }

    fun createSecondMessageUsageRestriction(): Restriction {
        val initialRestriction=Restriction.createInitialState()

        return initialRestriction.changeRestrict(RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT, 90L)
    }

    fun createMessagePermanentRestriction(): Restriction {
        val initialRestriction=Restriction.createInitialState()

        return initialRestriction.changeRestrict(RestrictionType.PERMANENT_BAN_MESSAGE_REPORT, Long.MAX_VALUE)
    }

    fun createVotePermanentRestriction(): Restriction {
        val initialRestriction=Restriction.createInitialState()

        return initialRestriction.changeRestrict(RestrictionType.PERMANENT_BAN_VOTE_REPORT, Long.MAX_VALUE)
    }

}
