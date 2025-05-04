package com.wespot.message.service.v2

import com.wespot.auth.service.SecurityUtils
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.message.port.`in`.UpdatedMessageV2UseCase
import com.wespot.message.port.out.MessageV2Port
import com.wespot.user.port.out.UserPort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdatedMessageV2Service(
    private val userPort: UserPort,
    private val messageV2Port: MessageV2Port,
) : UpdatedMessageV2UseCase {

    @Transactional
    override fun bookmarkMessage(messageId: Long) {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)

        val message = messageV2Port.findById(messageId)

        if (!message.isRoom()) {
            throw CustomException(
                message = "쪽지 방만을 통해 즐겨찾기를 진행할 수 있습니다.",
                status = HttpStatus.BAD_REQUEST,
                view = ExceptionView.TOAST
            )
        }

        message.bookmark(viewer = loginUser)
        messageV2Port.save(message)
    }

}
