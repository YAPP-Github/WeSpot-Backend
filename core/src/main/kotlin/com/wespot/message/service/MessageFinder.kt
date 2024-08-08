package com.wespot.message.service

import com.wespot.message.port.out.MessagePort
import com.wespot.school.port.out.SchoolPort
import com.wespot.user.port.out.BlockedUserPort
import com.wespot.user.port.out.UserPort

object MessageFinder {

    fun findMessageById(id: Long, messagePort: MessagePort) = messagePort.findById(id)
        ?: throw NoSuchElementException("메시지를 찾을 수 없습니다.")

    fun findUserById(id: Long, userPort: UserPort) = userPort.findById(id)
        ?: throw NoSuchElementException("유저를 찾을 수 없습니다.")

    fun findSchoolById(schoolId: Long, schoolPort: SchoolPort) = schoolPort.findById(schoolId)
        ?: throw NoSuchElementException("학교를 찾을 수 없습니다.")

    fun findAllByBlockerId(blockerId: Long, blockedUserPort: BlockedUserPort) =
        blockedUserPort.findAllByBlockerId(blockerId)
}
