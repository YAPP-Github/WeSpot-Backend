package com.wespot.user.mapper

import com.wespot.user.entity.RestrictionJpaEntity
import com.wespot.user.restriction.MessageRestriction
import com.wespot.user.restriction.Restriction
import com.wespot.user.restriction.VoteRestriction

object RestrictionMapper {

    fun mapToDomainEntity(restrictionJpaEntity: RestrictionJpaEntity) =
        Restriction(
            id = restrictionJpaEntity.id,
            voteRestriction = VoteRestriction(
                restrictionType = restrictionJpaEntity.voteRestrictionType,
                releaseDate = restrictionJpaEntity.voteReleaseDate
            ),
            messageRestriction = MessageRestriction(
                restrictionType = restrictionJpaEntity.messageRestrictionType,
                releaseDate = restrictionJpaEntity.messageReleaseDate
            )
        )

    fun mapToJpaEntity(restriction: Restriction) =
        RestrictionJpaEntity(
            id = restriction.id,
            voteRestrictionType = restriction.voteRestriction.restrictionType,
            voteReleaseDate = restriction.voteRestriction.releaseDate,
            messageRestrictionType = restriction.messageRestriction.restrictionType,
            messageReleaseDate = restriction.messageRestriction.releaseDate
        )
}
