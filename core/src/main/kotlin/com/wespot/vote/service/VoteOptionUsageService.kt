package com.wespot.vote.service

import com.wespot.exception.CustomException
import com.wespot.user.User
import com.wespot.vote.port.`in`.VoteOptionUsageUseCase
import com.wespot.vote.port.out.VotePort
import com.wespot.voteoption.VoteOption
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class VoteOptionUsageService(
    private val votePort: VotePort,
) : VoteOptionUsageUseCase {

    @Transactional(readOnly = true)
    override fun findLeastFrequentlyUsedVoteOptionsAt(user: User, index: Int): VoteOption {
        val today = LocalDate.now()
        val vote = votePort.findBySchoolIdAndGradeAndClassNumberAndDate(
            schoolId = user.school.id,
            grade = user.grade,
            classNumber = user.classNumber,
            date = today
        ) ?: throw CustomException(message = "오늘의 투표가 존재하지 않습니다.")

        return vote.leastFrequentlyUsedVoteOptionsAt(index)
    }

}
