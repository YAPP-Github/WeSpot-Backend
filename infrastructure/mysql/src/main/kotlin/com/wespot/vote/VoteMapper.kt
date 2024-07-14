package com.wespot.vote

object VoteMapper {

    fun mapToDomainEntity(voteJpaEntity: VoteJpaEntity, ballots: List<Ballot>): Vote =
        Vote(
            id = voteJpaEntity.id,
            schoolId = voteJpaEntity.schoolId,
            grade = voteJpaEntity.grade,
            groupNumber = voteJpaEntity.groupNumber,
            voteNumber = voteJpaEntity.voteNumber,
            date = voteJpaEntity.date,
            ballots = Ballots.from(ballots)
        )

    fun mapToJpaEntity(vote: Vote): VoteJpaEntity =
        VoteJpaEntity(
            id = vote.id,
            schoolId = vote.schoolId,
            grade = vote.grade,
            groupNumber = vote.groupNumber,
            voteNumber = vote.voteNumber,
            date = vote.date,
        )

}