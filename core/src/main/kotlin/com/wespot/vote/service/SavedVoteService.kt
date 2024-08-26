package com.wespot.vote.service

import com.wespot.EventUtils
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import com.wespot.vote.Vote
import com.wespot.vote.dto.request.VoteRequest
import com.wespot.vote.dto.request.VoteRequests
import com.wespot.vote.dto.response.SavedVoteResponse
import com.wespot.vote.dto.response.VoteItems
import com.wespot.vote.event.RegisteredVoteEvent
import com.wespot.vote.port.`in`.SavedVoteUseCase
import com.wespot.vote.port.out.VotePort
import com.wespot.vote.service.helper.VoteServiceHelper
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class SavedVoteService(
    private val votePort: VotePort,
    private val userPort: UserPort,
) : SavedVoteUseCase {

    @Transactional(readOnly = true)
    override fun getVoteOptions(): VoteItems {
        val user = VoteServiceHelper.findLoginUser(userPort)
        val classmates = VoteServiceHelper.findClassmatesByUser(userPort, user)
        val today = LocalDate.now()

        val vote: Vote = VoteServiceHelper.findVoteByUser(votePort, user, today)
        val usersForVote: List<User> = vote.findUsersForVote(classmates, user)

        return VoteItems.of(
            classmates = usersForVote,
            voteOptionsByVoteDate = vote.voteOptionsByVoteDate
        )
    }

    @Transactional
    override fun saveVote(
        requests: VoteRequests
    ): SavedVoteResponse {
        val user = VoteServiceHelper.findLoginUser(userPort)
        val receivers = getVotedUsers(requests.votes)
        val voteTime = LocalDateTime.now()
        val vote: Vote = VoteServiceHelper.findVoteByUser(votePort, user, voteTime.toLocalDate())
        requests.votes
            .forEach { request ->
                vote.addBallot(
                    request.voteOptionId,
                    user,
                    receivers.find { it.id == request.userId }
                        ?: throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.DIALOG, "투표 대상을 찾을 수 없습니다."),
                    voteTime)
            }
        EventUtils.publish(RegisteredVoteEvent(user, vote))

        return SavedVoteResponse(votePort.save(vote).id)
    }

    private fun getVotedUsers(requests: List<VoteRequest>): List<User> {
        validateRequestsSize(requests.size)
        val userIds: List<Long> = requests.stream()
            .map { it.userId }
            .toList()

        return userPort.findByIdIn(userIds)
    }

    private fun validateRequestsSize(requestsSize: Int) {
        if (5 < requestsSize) {
            throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "투표는 한번에 최대 5명에게 할 수 있습니다.")
        }
    }

}
