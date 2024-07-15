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
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class VoteService(
    private val votePort: VotePort,
    private val voteOptionPort: VoteOptionPort,
    private val userPort: UserPort,
) : VoteUseCase {

    @Transactional
    override fun getVoteOptions(userId: Long): VoteItems {
        val user: User = findUser(userId)
        val classmates = findClassmatesByUser(user)
        val today = LocalDate.now()
        val vote: Vote = findVoteByUser(user, today)
        val todayVoteOptions: List<VoteOption> = findTodayVoteOptions(vote)
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

    private fun findTodayVoteOptions(vote: Vote): List<VoteOption> {
        val voteOptions: List<VoteOption> = voteOptionPort.findAllVoteOption()

        return vote.findTodayVoteOptions(voteOptions)
    }

    @Transactional
    override fun saveVote(
        userId: Long,
        requests: List<VoteRequest>
    ): Long {
        validateUserIdsInRequests(requests)
        val user: User = findUser(userId)
        val today = LocalDate.now()
        val vote: Vote = findVoteByUser(user, today)
        requests.stream()
            .forEach { request ->
                vote.addBallot(
                    todayVoteOptionsIds = findTodayVoteOptionIds(vote),
                    voteOptionId = request.voteOptionId,
                    senderId = userId,
                    receiverId = request.userId
                )
            }

        return votePort.save(vote).id
    }

    private fun validateUserIdsInRequests(requests: List<VoteRequest>) {
        val userIds: List<Long> = requests.stream()
            .map { it.userId }
            .toList()
        validateUserIds(userIds)
    }

    private fun validateUserIds(userIds: List<Long>) {
        val foundUserIds = userPort.findIdsByIdIn(userIds)
        if (foundUserIds.size == userIds.size) {
            return
        }
        throw IllegalArgumentException("투표하고자 하는 회원이 존재하지 않습니다.")
    }

    private fun findTodayVoteOptionIds(vote: Vote): List<Long> {
        return findTodayVoteOptions(vote).stream()
            .map { it.id!! }
            .toList()
    }

}