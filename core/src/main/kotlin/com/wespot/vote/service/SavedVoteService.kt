package com.wespot.vote.service

import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import com.wespot.vote.Vote
import com.wespot.vote.dto.request.VoteRequest
import com.wespot.vote.dto.request.VoteRequests
import com.wespot.vote.dto.response.SaveVoteResponse
import com.wespot.vote.dto.response.VoteItems
import com.wespot.vote.event.ReceivedVoteEvent
import com.wespot.vote.event.RegisteredVoteEvent
import com.wespot.vote.port.`in`.SavedVoteUseCase
import com.wespot.vote.port.out.VotePort
import com.wespot.vote.service.helper.VoteServiceHelper
import jakarta.transaction.Transactional
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class SavedVoteService(
    private val votePort: VotePort,
    private val userPort: UserPort,
    private val eventPublisher: ApplicationEventPublisher,
) : SavedVoteUseCase {

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
    ): SaveVoteResponse {
        validateRequestsSize(requests.voteRequests.size)
        validateUserIdsInRequests(requests.voteRequests)
        val user = VoteServiceHelper.findLoginUser(userPort)
        val voteTime = LocalDateTime.now()
        val vote: Vote = VoteServiceHelper.findVoteByUser(votePort, user, voteTime.toLocalDate())
        requests.voteRequests
            .stream()
            .forEach { request -> addBallot(request, vote, user, voteTime) }
        eventPublisher.publishEvent(RegisteredVoteEvent(user, vote))

        return SaveVoteResponse(votePort.save(vote).id)
    }

    private fun addBallot(
        request: VoteRequest,
        vote: Vote,
        user: User,
        voteTime: LocalDateTime
    ) {
        val receiver = VoteServiceHelper.findUser(userPort, request.userId)
        eventPublisher.publishEvent(ReceivedVoteEvent(receiver))
        vote.addBallot(
            voteOptionId = request.voteOptionId,
            sender = user,
            receiver = receiver,
            voteTime = voteTime
        )
    }

    private fun validateRequestsSize(requestsSize: Int) {
        if (5 < requestsSize) {
            throw IllegalArgumentException("투표는 한번에 최대 5명에게 할 수 있습니다.")
        }
    }

    private fun validateUserIdsInRequests(requests: List<VoteRequest>) {
        val userIds: List<Long> = requests.stream()
            .map { it.userId }
            .toList()
        val foundUserIds = userPort.findIdsByIdIn(userIds)

        if (foundUserIds.size == userIds.size) {
            return
        }
        throw IllegalArgumentException("투표하고자 하는 회원이 존재하지 않습니다.")
    }

}
