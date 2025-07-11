package com.wespot.post.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.post.PostNotification
import com.wespot.post.port.`in`.PostNotificationUseCase
import com.wespot.post.port.out.PostNotificationPort
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostNotificationService(
    private val userPort: UserPort,
    private val postNotificationPort: PostNotificationPort
) : PostNotificationUseCase {

    @Transactional
    override fun toggleNotification(postId: Long) {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        postNotificationPort.findByPostIdAndUserId(postId = postId, userId = loginUser.id)
            ?.let { postNotificationPort.deleteById(it.id) }
            ?: postNotificationPort.save(
                PostNotification.of(
                    user = loginUser,
                    postId = postId
                )
            )
    }

}
