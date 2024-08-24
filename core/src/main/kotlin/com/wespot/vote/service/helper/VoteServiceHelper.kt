package com.wespot.vote.service.helper

import com.wespot.auth.service.SecurityUtils
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.User
import com.wespot.user.port.out.UserPort
import com.wespot.vote.Vote
import com.wespot.vote.port.out.VoteOptionPort
import com.wespot.vote.port.out.VotePort
import com.wespot.voteoption.VoteOption
import org.springframework.http.HttpStatus
import java.time.LocalDate

object VoteServiceHelper {

    fun findLoginUser(userPort: UserPort): User {
        return SecurityUtils.getLoginUser(userPort)
    }

    fun findUser(userPort: UserPort, userId: Long): User {
        return userPort.findById(userId)
            ?: throw CustomException(HttpStatus.NOT_FOUND, ExceptionView.TOAST, "ID에 해당하는 사용자가 존재하지 않습니다.")
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
        ) ?: throw CustomException(HttpStatus.NOT_FOUND, ExceptionView.TOAST, "해당 투표가 존재하지 않습니다.")
    }

    fun findVoteOptionById(voteOptionPort: VoteOptionPort, optionId: Long): VoteOption {
        return voteOptionPort.findById(optionId)
            ?: throw CustomException(HttpStatus.NOT_FOUND, ExceptionView.TOAST, "ID에 해당하는 선택지가 존재하지 않습니다.")
    }

    fun findVotesReceiverIdOrderByDateDesc(votePort: VotePort, user: User, cursorId: Long, limit: Long): List<Vote> {
        return votePort.findAllBySchoolIdAndGradeAndClassNumberAndReceiverIdOrderByDateDesc(
            schoolId = user.schoolId,
            grade = user.grade,
            classNumber = user.classNumber,
            receiverId = user.id,
            cursorId = cursorId,
            limit = limit
        )
    }

    fun findVotesSenderIdOrderByDateDesc(
        votePort: VotePort,
        user: User,
        cursorId: Long,
        limit: Long
    ): List<Vote> {
        return votePort.findAllBySchoolIdAndGradeAndClassNumberAndSenderIdOrderByDateDesc(
            schoolId = user.schoolId,
            grade = user.grade,
            classNumber = user.classNumber,
            senderId = user.id,
            cursorId = cursorId,
            limit = limit
        )
    }

}
