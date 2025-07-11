package com.wespot.post.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.exception.CustomException
import com.wespot.post.PostNotification
import com.wespot.post.port.`in`.PostNotificationUseCase
import com.wespot.post.port.out.PostNotificationPort
import com.wespot.post.port.out.PostPort
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostNotificationService(
    private val userPort: UserPort,
    private val postNotificationPort: PostNotificationPort,
    private val postPort: PostPort
) : PostNotificationUseCase {

    @Transactional
    override fun toggleNotification(postId: Long) {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val post = postPort.findById(postId = postId) ?: throw CustomException(message = "존재하지 않는 게시글입니다.")
        postNotificationPort.findByPostIdAndUserId(postId = post.id, userId = loginUser.id)
            ?.let { postNotificationPort.deleteById(it.id) }
            ?: postNotificationPort.save(
                PostNotification.of(
                    user = loginUser,
                    postId = post.id
                )
            )
    }

}
