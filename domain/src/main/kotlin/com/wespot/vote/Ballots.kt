package com.wespot.vote

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import org.springframework.http.HttpStatus
import java.util.*

data class Ballots(
    val ballots: MutableMap<Long, MutableMap<Long, Ballot>>,
) {

    companion object {
        fun from(ballots: List<Ballot>): Ballots {
            return Ballots(createBallots(ballots))
        }

        private fun createBallots(ballots: List<Ballot>): MutableMap<Long, MutableMap<Long, Ballot>> {
            val resultOfBallots: MutableMap<Long, MutableMap<Long, Ballot>> = LinkedHashMap()
            ballots.forEach { addBallot(resultOfBallots, it) }
            return resultOfBallots
        }

        private fun addBallot(
            resultOfBallots: MutableMap<Long, MutableMap<Long, Ballot>>,
            ballot: Ballot
        ) {
            val ballots = resultOfBallots.computeIfAbsent(ballot.senderId) { LinkedHashMap() }
            validateDuplicateVote(ballots, ballot)
            ballots[ballot.receiverId] = ballot
        }

        private fun validateDuplicateVote(
            ballots: MutableMap<Long, Ballot>,
            ballot: Ballot
        ) {
            if (ballots.contains(ballot.receiverId)) {
                throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "하루에 한 명의 회원에게 한 개의 투표만 할 수 있습니다.")
            }
        }

        fun createEmptyBallots(): Ballots {
            return Ballots(LinkedHashMap())
        }
    }

    fun add(ballot: Ballot) {
        validateNull(ballot)
        validateDuplicateVote(ballot)
        ballots.computeIfAbsent(ballot.senderId) { LinkedHashMap() }[ballot.receiverId] = ballot
    }

    private fun validateNull(ballot: Ballot) {
        if (Objects.isNull(ballot)) {
            throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "빈 투표지를 제출할 수 없습니다.")
        }
    }

    private fun validateDuplicateVote(ballot: Ballot) {
        if (isVoteSameClassmate(ballot)) {
            throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "하루에 한 명의 회원에게 한 개의 투표만 할 수 있습니다.")
        }
    }

    private fun isVoteSameClassmate(ballot: Ballot) =
        ballots.contains(ballot.senderId) && ballots[ballot.senderId]!!.contains(ballot.receiverId)

    fun findUserIdsVotedByUser(sentUserId: Long): List<Long> {
        return ballots.computeIfAbsent(sentUserId) { LinkedHashMap() }
            .map { it.key }
            .toList()
    }

    fun findSentBallotsByUser(userId: Long): List<Ballot> {
        if (!ballots.containsKey(userId)) {
            return Collections.emptyList()
        }

        return ballots[userId]!!.values
            .toList()
    }

    fun getNumberOfSender(): Int {
        return ballots.size
    }

}
