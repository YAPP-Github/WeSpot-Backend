package com.wespot.admin.service

import com.wespot.admin.dto.CreatedVoteOptionRequest
import com.wespot.admin.dto.UpdateVoteOptionRequest
import com.wespot.admin.dto.VoteOptionResponses
import com.wespot.admin.port.`in`.AdminVoteOptionUseCase
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.vote.port.out.VoteOptionPort
import com.wespot.voteoption.VoteOption
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminVoteOptionService(
    private val voteOptionPort: VoteOptionPort
) : AdminVoteOptionUseCase {

    @Transactional(readOnly = true)
    override fun getVoteOptions(): VoteOptionResponses {
        val voteOptions = voteOptionPort.findAll()

        return VoteOptionResponses.from(voteOptions)
    }

    @Transactional
    override fun createVoteOption(request: CreatedVoteOptionRequest): Long {
        val voteOption = VoteOption.createInitialState(request.content)

        return voteOptionPort.save(voteOption).id
    }

    @Transactional
    override fun createVoteOptions(requests: List<CreatedVoteOptionRequest>): List<Long> {
        val voteOptions = requests.map { VoteOption.createInitialState(it.content) }

        return voteOptionPort.saveAll(voteOptions).map { it.id }
    }

    @Transactional
    override fun updateVoteOption(voteOptionId: Long, request: UpdateVoteOptionRequest): Long {
        val voteOption = voteOptionPort.findById(voteOptionId)
            ?: throw CustomException(
                message = "ID에 해당하는 질문지가 존재하지 않습니다.",
                view = ExceptionView.TOAST,
                status = HttpStatus.NOT_FOUND,
            )

        val updatedVoteOption = voteOption.update(request.content)
        return voteOptionPort.save(updatedVoteOption).id
    }

}
