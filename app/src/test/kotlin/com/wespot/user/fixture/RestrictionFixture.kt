package com.wespot.user.fixture

import com.wespot.user.Restriction
import com.wespot.user.RestrictionType

object RestrictionFixture {

    fun createFirstMessageUsageRestriction() = Restriction.of(RestrictionType.MESSAGE_USAGE, 30L)

    fun createSecondMessageUsageRestriction() = Restriction.of(RestrictionType.MESSAGE_USAGE, 90L)

    fun createMessagePermanentRestriction() = Restriction.of(RestrictionType.MESSAGE_PERMANENT, Long.MAX_VALUE)

    fun createVotePermanentRestriction() = Restriction.of(RestrictionType.VOTE_PERMANENT, Long.MAX_VALUE)

}
