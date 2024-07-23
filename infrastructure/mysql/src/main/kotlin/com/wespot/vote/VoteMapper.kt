package com.wespot.vote

object VoteMapper {

    fun mapToDomainEntity(voteJpaEntity: VoteJpaEntity, ballots: List<Ballot>): Vote =
        Vote(
            id = voteJpaEntity.id,
            schoolId = voteJpaEntity.schoolId,
            grade = voteJpaEntity.grade,
            classNumber = voteJpaEntity.classNumber,
            voteNumber = voteJpaEntity.voteNumber,
            date = voteJpaEntity.date,
            ballots = Ballots.from(ballots)
        )

    fun mapToJpaEntity(vote: Vote): VoteJpaEntity =
        VoteJpaEntity(
            id = vote.id,
            schoolId = vote.schoolId,
            grade = vote.grade,
            classNumber = vote.classNumber,
            voteNumber = vote.voteNumber,
            date = vote.date,
        )

}
