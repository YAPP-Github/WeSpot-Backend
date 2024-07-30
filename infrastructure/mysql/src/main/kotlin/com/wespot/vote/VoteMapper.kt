package com.wespot.vote

object VoteMapper {

    fun mapToDomainEntity(
        voteJpaEntity: VoteJpaEntity,
        voteOptionsByVoteDate: VoteOptionsByVoteDate,
        ballots: List<Ballot>
    ): Vote =
        Vote(
            id = voteJpaEntity.id,
            voteIdentifier = VoteIdentifier(
                schoolId = voteJpaEntity.schoolId,
                grade = voteJpaEntity.grade,
                classNumber = voteJpaEntity.classNumber,
                date = voteJpaEntity.date
            ),
            voteNumber = voteJpaEntity.voteNumber,
            voteOptionsByVoteDate = voteOptionsByVoteDate,
            ballots = Ballots.from(ballots)
        )

    fun mapToJpaEntity(vote: Vote): VoteJpaEntity =
        VoteJpaEntity(
            id = vote.id,
            schoolId = vote.voteIdentifier.schoolId,
            grade = vote.voteIdentifier.grade,
            classNumber = vote.voteIdentifier.classNumber,
            voteNumber = vote.voteNumber,
            date = vote.voteIdentifier.date,
        )

}
