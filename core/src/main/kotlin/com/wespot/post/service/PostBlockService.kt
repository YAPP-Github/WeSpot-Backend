package com.wespot.post.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.exception.CustomException
import com.wespot.post.PostBlock
import com.wespot.post.event.PostBlockEvent
import com.wespot.post.port.`in`.PostBlockUseCase
import com.wespot.post.port.out.PostBlockPort
import com.wespot.post.port.out.PostPort
import com.wespot.user.port.out.UserPort
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostBlockService(
    private val userPort: UserPort,
    private val postBlockPort: PostBlockPort,
    private val postPort: PostPort,
    private val applicationEventPublisher: ApplicationEventPublisher
) : PostBlockUseCase {

    @Transactional
    override fun blockPost(postId: Long) {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)

        if (loginUser.canNotUseCommunity()) {
            throw CustomException(status = HttpStatus.FORBIDDEN, message = "커뮤니티 이용이 제한된 사용자입니다.")
        }

        val post = postPort.findById(postId = postId) ?: throw CustomException(message = "존재하지 않는 게시글입니다.")
        if (post.isAuthor(loginUser.id)) {
            throw CustomException(message = "자신의 게시글은 차단할 수 없습니다.")
        }
        postBlockPort.findByPostIdAndUserId(postId = post.id, userId = loginUser.id)
            ?.let {
                postBlockPort.deleteById(it.id)
            }
            ?: run {
                postBlockPort.save(PostBlock(postId = post.id, userId = loginUser.id))
                val event = PostBlockEvent(
                    sender = loginUser,
                    receiver = post.user,
                    post = post
                )
                applicationEventPublisher.publishEvent(event)
            }
    }

}
