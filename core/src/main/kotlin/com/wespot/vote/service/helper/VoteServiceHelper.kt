package com.wespot.vote.service.helper

import com.wespot.auth.service.SecurityUtils
import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import com.wespot.vote.Vote
import com.wespot.vote.VoteOptionsByVoteDate
import com.wespot.vote.port.out.VoteOptionPort
import com.wespot.vote.port.out.VotePort
import com.wespot.voteoption.VoteOption
import java.time.LocalDate

object VoteServiceHelper {

    fun findLoginUserId(userPort: UserPort): Long {
        return SecurityUtils.getLoginUserId(userPort)
    }

    fun findUser(userPort: UserPort, userId: Long): User {
        return userPort.findById(userId)
            ?: throw IllegalArgumentException("ID에 해당하는 사용자가 존재하지 않습니다.")
    }

    fun findClassmatesByUser(userPort: UserPort, user: User): List<User> {
        return userPort.findAllBySchoolIdAndGradeAndClassNumber(
            schoolId = user.schoolId,
            grade = user.grade,
            classNumber = user.classNumber
        )
    }

    fun findVoteByUser(votePort: VotePort, user: User, date: LocalDate): Vote {
        return votePort.findBySchoolIdAndGradeAndClassNumberAndDate(
            schoolId = user.schoolId,
            grade = user.grade,
            classNumber = user.classNumber,
            date = date
        ) ?: throw IllegalArgumentException("해당 투표가 존재하지 않습니다.")
    }

    fun findVoteOptionById(voteOptionPort: VoteOptionPort, optionId: Long): VoteOption {
        return voteOptionPort.findById(optionId)
            ?: throw IllegalArgumentException("ID에 해당하는 선택지가 존재하지 않습니다.")
    }

    fun findVotesOrderByDateDesc(votePort: VotePort, user: User): List<Vote> {
        return votePort.findAllBySchoolIdAndGradeAndClassNumberByOrderByDateDesc(
            schoolId = user.schoolId,
            grade = user.grade,
            classNumber = user.classNumber
        )
    }

    fun findVoteOptionOnVoteOptions(
        voteOptions: VoteOptionsByVoteDate,
        optionId: Long
    ): VoteOption {
        val voteOption = voteOptions.voteOptionsByVoteDate
            .map { it.voteOption }
            .find { it.id == optionId }
            ?: throw IllegalArgumentException("오늘의 선택지가 아닙니다.")

        return voteOption
    }

}
