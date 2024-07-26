package com.wespot.user.fixture

import com.wespot.user.Restriction
import com.wespot.user.RestrictionType

object RestrictionFixture {

    fun createFirstMessageUsageRestriction() = Restriction.of(RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT, 30L)

    fun createSecondMessageUsageRestriction() = Restriction.of(RestrictionType.TEMPORARY_BAN_MESSAGE_REPORT, 90L)

    fun createMessagePermanentRestriction() = Restriction.of(RestrictionType.PERMANENT_BAN_MESSAGE_REPORT, Long.MAX_VALUE)

    fun createVotePermanentRestriction() = Restriction.of(RestrictionType.PERMANENT_BAN_VOTE_REPORT, Long.MAX_VALUE)

}
