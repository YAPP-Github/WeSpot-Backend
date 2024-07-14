package com.wespot.vote.service

import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import com.wespot.vote.Vote
import com.wespot.vote.dto.request.VoteRequest
import com.wespot.vote.dto.response.VoteItems
import com.wespot.vote.port.`in`.VoteUseCase
import com.wespot.vote.port.out.VoteOptionPort
import com.wespot.vote.port.out.VotePort
import com.wespot.voteoption.VoteOption
import jakarta.transaction.Transactional
import java.time.LocalDate

class VoteService(
    private val votePort: VotePort,
    private val voteOptionPort: VoteOptionPort,
    private val userPort: UserPort,
) : VoteUseCase {

    @Transactional
    override fun getVoteOptions(userId: Long): VoteItems {
        val voteOptions: List<VoteOption> = voteOptionPort.findAllVoteOption()
        val user: User = findUser(userId)
        val classmates = findClassmatesByUser(user)
        val vote: Vote = findVoteByUser(user, LocalDate.now())
        val todayVoteOptions: List<VoteOption> = vote.findTodayVoteOptions(voteOptions)
        val todayVoteItems: List<User> = vote.findVotedUsers(classmates, user)

        return VoteItems.of(todayVoteItems, todayVoteOptions)
    }

    private fun findUser(userId: Long): User {
        return userPort.findById(userId)
            ?: throw IllegalArgumentException("ID에 해당하는 사용자가 존재하지 않습니다.")
    }

    private fun findClassmatesByUser(user: User): List<User> {
        return userPort.findAllBySchoolIdAndGradeAndGroupNumber(
            user.id,
            user.grade,
            user.groupNumber
        )
    }

    private fun findVoteByUser(user: User, date: LocalDate): Vote {
        return votePort.findBySchoolIdAndGradeAndGroupNumberAndDate(
            user.schoolId,
            user.grade,
            user.groupNumber,
            date
        ) ?: throw IllegalArgumentException("해당 투표가 존재하지 않습니다.")
    }

    @Transactional
    override fun saveVote(userId: Long, requests: List<VoteRequest>): Long {
        val user: User = findUser(userId)
        val vote: Vote = findVoteByUser(user, LocalDate.now())
        requests.stream()
            .forEach { request ->
                vote.addBallot(
                    voteOptionId = request.voteOptionId,
                    senderId = userId,
                    receiverId = request.userId
                )
            }
        return votePort.save(vote).id
    }

}