package com.wespot.vote

import com.wespot.user.User
import com.wespot.voteoption.VoteOption
import java.time.LocalDate

data class Vote(
    val id: Long,
    val schoolId: Long,
    val grade: Int,
    val classNumber: Int,
    val voteNumber: Int,
    val date: LocalDate,
    val ballots: Ballots,
) {

    companion object {
        private const val NUMBER_OF_VOTE_USERS = 5
    }

    fun findVoteOptionsByVoteDate(allVoteOptions: List<VoteOption>): VoteOptionsByVoteDate {
        return VoteOptionsByVoteDate.of(date, voteNumber, allVoteOptions)
    }


    fun findUsersForVote(classmates: List<User>, user: User): List<User> {
        val alreadyVotedByUser: List<Long> = ballots.findUserIdsVotedByUser(user.id)
        return classmates.stream()
            .filter { !alreadyVotedByUser.contains(it.id) && isNotMe(it, user) }
            .toList()
            .take(NUMBER_OF_VOTE_USERS)
    }

    private fun isNotMe(classmate: User, user: User) = classmate != user

    fun addBallot(
        voteOptionsByVoteDate: VoteOptionsByVoteDate,
        voteOptionId: Long,
        senderId: Long,
        receiverId: Long
    ) {
        voteOptionsByVoteDate.validateVoteOption(voteOptionId)
        ballots.add(
            Ballot.of(
                voteId = id,
                voteOptionId = voteOptionId,
                senderId = senderId,
                receiverId = receiverId
            )
        )
    }

    fun getBallots(): List<Ballot> {
        return ballots.ballots
            .map { it.value }
            .flatMap { it.values }
            .toList()
    }

    fun getRankedVoteResults(
        voteOptionsByVoteDate: VoteOptionsByVoteDate,
        users: List<User>,
        rankCalculateService: RankCalculateService
    ): Map<VoteOption, List<VoteRecord>> {
        val rankedVoteResults = rankCalculateService.calculate(users, getBallots())

        return voteOptionsByVoteDate.voteOptions
            .associateWith { rankedVoteResults[it.id] ?: emptyList() }
            .toMap(LinkedHashMap())
    }

    fun getUserReceivedVotes(
        voteOptionsByVoteDate: VoteOptionsByVoteDate,
        user: User,
        receivedVoteCalculateService: ReceivedVoteCalculateService
    ): Map<VoteOption, VoteRecord> {
        val receivedVotes = receivedVoteCalculateService.calculateUserReceivedVotes(user, getBallots())

        return voteOptionsByVoteDate.voteOptions
            .filter { receivedVotes.contains(it.id) }
            .associateWith { receivedVotes[it.id]!! }
            .toMap(LinkedHashMap())
    }

    fun getUserReceivedVote(
        voteOptionsByVoteDate: VoteOptionsByVoteDate,
        voteOption: VoteOption,
        user: User,
        receivedVoteCalculateService: ReceivedVoteCalculateService
    ): VoteRecord {
        voteOptionsByVoteDate.validateVoteOption(voteOption.id)

        return receivedVoteCalculateService.calculateUserReceivedVote(voteOption, user, getBallots())
    }

    fun getUserSentVotes(
        voteOptionsByVoteDate: VoteOptionsByVoteDate,
        user: User,
    ): Map<VoteOption, List<Ballot>> {
        val ballots = ballots.findSentBallotsByUser(user.id)

        return voteOptionsByVoteDate.voteOptions
            .filter { voteOption -> containVoteOptionOnBallots(ballots, voteOption) }
            .associateWith { voteOption -> ballots.filter { voteOption.id == it.voteOptionId } }
    }

    private fun containVoteOptionOnBallots(
        ballots: List<Ballot>,
        voteOption: VoteOption
    ) = ballots.stream()
        .anyMatch { it.voteOptionId == voteOption.id }

    fun getUserSentVote(
        voteOptionsByVoteDate: VoteOptionsByVoteDate,
        voteOption: VoteOption,
        user: User,
    ): List<Ballot> {
        voteOptionsByVoteDate.validateVoteOption(voteOption.id)

        return ballots.findSentBallotsByUser(user.id)
            .filter { it.voteOptionId == voteOption.id }
    }

}
