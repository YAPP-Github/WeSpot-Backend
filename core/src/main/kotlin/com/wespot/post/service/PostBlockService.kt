package com.wespot.post.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.exception.CustomException
import com.wespot.post.PostBlock
import com.wespot.post.port.`in`.PostBlockUseCase
import com.wespot.post.port.out.PostBlockPort
import com.wespot.post.port.out.PostPort
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostBlockService(
    private val userPort: UserPort,
    private val postBlockPort: PostBlockPort,
    private val postPort: PostPort,
) : PostBlockUseCase {

    @Transactional
    override fun blockPost(postId: Long) {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val post = postPort.findById(postId = postId) ?: throw CustomException(message = "존재하지 않는 게시글입니다.")
        postBlockPort.findByPostIdAndUserId(postId = post.id, userId = loginUser.id)
            ?.let {
                postBlockPort.deleteById(it.id)
            }
            ?: {
                postBlockPort.save(PostBlock(postId = post.id, userId = loginUser.id))
            }
    }

}
