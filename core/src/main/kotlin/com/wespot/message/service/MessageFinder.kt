package com.wespot.message.service

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.message.port.out.MessagePort
import com.wespot.school.port.out.SchoolPort
import com.wespot.user.port.out.BlockedUserPort
import com.wespot.user.port.out.UserPort
import feign.FeignException.NotFound
import org.springframework.http.HttpStatus

object MessageFinder {

    fun findMessageById(id: Long, messagePort: MessagePort) = messagePort.findById(id)
        ?: throw CustomException(HttpStatus.NOT_FOUND, ExceptionView.TOAST, "메시지를 찾을 수 없습니다.")

    fun findUserById(id: Long, userPort: UserPort) = userPort.findById(id)
        ?: throw CustomException(HttpStatus.NOT_FOUND, ExceptionView.TOAST, "유저를 찾을 수 없습니다.")

    fun findSchoolById(schoolId: Long, schoolPort: SchoolPort) = schoolPort.findById(schoolId)
        ?: throw CustomException(HttpStatus.NOT_FOUND, ExceptionView.TOAST, "학교를 찾을 수 없습니다.")

    fun findAllByBlockerId(blockerId: Long, blockedUserPort: BlockedUserPort) =
        blockedUserPort.findAllByBlockerId(blockerId)
}
