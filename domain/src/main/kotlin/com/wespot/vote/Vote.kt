package com.wespot.vote

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.User
import com.wespot.vote.event.ReceivedVoteEvent
import com.wespot.voteoption.VoteOption
import org.springframework.data.domain.AbstractAggregateRoot
import org.springframework.http.HttpStatus
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class Vote(
    val id: Long,
    val voteIdentifier: VoteIdentifier,
    val voteNumber: Int,
    val voteOptionsByVoteDate: VoteOptionsByVoteDate,
    val ballots: Ballots,
) : AbstractAggregateRoot<Vote>() {

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

            throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "입력된 이전 투표가 유효하지 않습니다.")
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
        validateClassmate(user)
        classmates.forEach { validateClassmate(it) }
        val alreadyVotedByUser: List<Long> = ballots.findUserIdsVotedByUser(user.id)

        return classmates.stream()
            .filter { !alreadyVotedByUser.contains(it.id) && isNotMe(it, user) }
            .filter { it.isRegulation() }
            .toList()
            .shuffled()
            .take(NUMBER_OF_VOTE_USERS)
    }

    private fun isNotMe(classmate: User, user: User) = classmate != user

    fun addBallot(
        voteOptionId: Long,
        sender: User,
        receiver: User,
        voteTime: LocalDateTime
    ) {
        voteOptionsByVoteDate.validateVoteOption(voteOptionId)
        validateClassmate(sender)
        validateReceiver(receiver)

        ballots.add(
            Ballot.of(
                voteId = this.id,
                voteDate = voteIdentifier.date,
                voteOptionId = voteOptionId,
                senderId = sender.id,
                receiverId = receiver.id,
                voteTime = voteTime
            )
        )

        registerEvent(ReceivedVoteEvent(receiver))
    }

    private fun validateClassmate(user: User) {
        val userVoteIdentifier = VoteIdentifier.of(user, voteIdentifier.date)
        require(voteIdentifier.isSameClass(userVoteIdentifier)) {
            throw CustomException(
                HttpStatus.BAD_REQUEST,
                ExceptionView.TOAST,
                "다른 반의 학생이(을) 투표할 수 없습니다."
            )
        }
    }

    private fun validateReceiver(receiver: User) {
        validateClassmate(receiver)
        if (receiver.isWithDraw()) {
            throw CustomException(
                HttpStatus.BAD_REQUEST,
                ExceptionView.TOAST,
                "탈퇴한 학생에게 투표할 수 없습니다."
            )
        }
        if (receiver.isKeepRestrict()) {
            throw CustomException(
                HttpStatus.BAD_REQUEST,
                ExceptionView.TOAST,
                "이용제한을 받은 학생에게 투표할 수 없습니다."
            )
        }
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
        classmates: List<User>
    ): List<CompleteBallot> {
        val ballots = ballots.findSentBallotsByUser(user.id)
        val classmateGroup = classmates.associateBy { it.id }
        val voteOptionGroup = voteOptionsByVoteDate.voteOptionsByVoteDate
            .map { it.voteOption }
            .associateBy { it.id }

        return ballots.filter { voteOptionGroup.containsKey(it.voteOptionId) }
            .filter { classmateGroup.containsKey(it.receiverId) }
            .map {
                CompleteBallot.of(
                    this, voteOptionGroup[it.voteOptionId]!!, user, classmateGroup[it.receiverId]!!, it
                )
            }.toList()
    }

    fun getNumberOfSender(): Int {
        return ballots.getNumberOfSender()
    }

}
