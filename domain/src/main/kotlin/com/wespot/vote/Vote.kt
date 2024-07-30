package com.wespot.vote

import com.wespot.user.User
import com.wespot.voteoption.VoteOption
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class Vote(
    val id: Long,
    val voteIdentifier: VoteIdentifier,
    val voteNumber: Int,
    val voteOptionsByVoteDate: VoteOptionsByVoteDate,
    val ballots: Ballots,
) {

    companion object {
        private const val NUMBER_OF_VOTE_USERS = 5
        private const val MOVED_YESTERDAY = 1L

        fun of(
            voteIdentifier: VoteIdentifier,
            allVoteOptions: List<VoteOption>,
            previousVote: Vote?,
        ): Vote {
            if (Objects.isNull(previousVote)) {
                return Vote(
                    id = 0L,
                    voteIdentifier = voteIdentifier,
                    voteNumber = 0,
                    voteOptionsByVoteDate = findVoteOptionsByVoteDate(voteIdentifier.date, 0, allVoteOptions),
                    ballots = Ballots.createEmptyBallots()
                )
            }

            validatePreviousVote(voteIdentifier, previousVote!!)
            return Vote(
                id = 0L,
                voteIdentifier = voteIdentifier,
                voteNumber = previousVote.voteNumber + 1,
                voteOptionsByVoteDate = findVoteOptionsByVoteDate(
                    voteIdentifier.date,
                    previousVote.voteNumber + 1,
                    allVoteOptions
                ),
                ballots = Ballots.createEmptyBallots()
            )
        }

        private fun findVoteOptionsByVoteDate(
            date: LocalDate,
            voteNumber: Int,
            allVoteOptions: List<VoteOption>
        ): VoteOptionsByVoteDate {
            return VoteOptionsByVoteDate.createInitialVoteOptionsByVoteDate(
                voteId = 0L,
                date = date,
                voteNumber = voteNumber,
                allVoteOptions = allVoteOptions
            )
        }

        private fun validatePreviousVote(voteIdentifier: VoteIdentifier, previousVote: Vote) {
            if (voteIdentifier.isSameClass(previousVote.voteIdentifier) && isYesterday(voteIdentifier, previousVote)) {
                return
            }

            throw IllegalArgumentException("입력된 이전 투표가 유효하지 않습니다.")
        }

        private fun isYesterday(
            voteIdentifier: VoteIdentifier,
            previousVote: Vote
        ): Boolean {
            val yesterday = voteIdentifier.date.minusDays(MOVED_YESTERDAY)

            return previousVote.voteIdentifier.date == yesterday
        }
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
        voteOptionId: Long,
        senderId: Long,
        receiverId: Long,
        voteTime: LocalDateTime
    ) {
        voteOptionsByVoteDate.validateVoteOption(voteOptionId)
        ballots.add(
            Ballot.of(
                voteId = this.id,
                voteDate = voteIdentifier.date,
                voteOptionId = voteOptionId,
                senderId = senderId,
                receiverId = receiverId,
                voteTime = voteTime
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
        users: List<User>,
        rankCalculateService: RankCalculateService
    ): Map<VoteOption, List<VoteRecord>> {
        val rankedVoteResults = rankCalculateService.calculate(users, getBallots())

        return voteOptionsByVoteDate.voteOptionsByVoteDate
            .associateWith { rankedVoteResults[it.voteOption.id] ?: emptyList() }
            .mapKeys { it.key.voteOption }
            .toMap(LinkedHashMap())
    }

    fun getUserReceivedVotes(
        user: User,
        receivedVoteCalculateService: ReceivedVoteCalculateService
    ): Map<VoteOption, VoteRecord> {
        val receivedVotes = receivedVoteCalculateService.calculateUserReceivedVotes(user, getBallots())

        return voteOptionsByVoteDate.voteOptionsByVoteDate
            .filter { receivedVotes.contains(it.voteOption.id) }
            .associateWith { receivedVotes[it.voteOption.id]!! }
            .mapKeys { it.key.voteOption }
            .toMap(LinkedHashMap())
    }

    fun getUserReceivedVote(
        voteOption: VoteOption,
        user: User,
        receivedVoteCalculateService: ReceivedVoteCalculateService
    ): VoteRecord {
        voteOptionsByVoteDate.validateVoteOption(voteOption.id)

        return receivedVoteCalculateService.calculateUserReceivedVote(voteOption, user, getBallots())
    }

    fun getUserSentVotes(
        user: User,
    ): Map<VoteOption, List<Ballot>> {
        val ballots = ballots.findSentBallotsByUser(user.id)

        return voteOptionsByVoteDate.voteOptionsByVoteDate
            .filter { containVoteOptionOnBallots(ballots, it.voteOption) }
            .map { it.voteOption }
            .associateWith { voteOption -> ballots.filter { voteOption.id == it.voteOptionId } }
    }

    private fun containVoteOptionOnBallots(
        ballots: List<Ballot>,
        voteOption: VoteOption
    ) = ballots.stream()
        .anyMatch { it.voteOptionId == voteOption.id }

    fun getUserSentVote(
        voteOption: VoteOption,
        user: User,
    ): List<Ballot> {
        voteOptionsByVoteDate.validateVoteOption(voteOption.id)

        return ballots.findSentBallotsByUser(user.id)
            .filter { it.voteOptionId == voteOption.id }
    }

}
