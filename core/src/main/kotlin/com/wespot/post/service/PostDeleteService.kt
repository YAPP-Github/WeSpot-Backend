package com.wespot.post.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.exception.CustomException
import com.wespot.post.event.PostDeletedEvent
import com.wespot.post.port.`in`.PostDeleteUseCase
import com.wespot.post.port.out.PostPort
import com.wespot.user.port.out.UserPort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostDeleteService(
    private val userPort: UserPort,
    private val postPort: PostPort,
) : PostDeleteUseCase {

    @Transactional
    override fun deletePost(postId: Long) {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val post = postPort.findById(postId = postId) ?: throw IllegalArgumentException("존재하지 않는 게시글입니다.")

        if (post.isAuthor(loginUser.id)) {
            postPort.deleteById(post.id)
            PostDeletedEvent(deleterId = loginUser.id, deletedPost = post)
            return
        }

        throw CustomException(status = HttpStatus.FORBIDDEN, message = "게시글 작성자가 아닙니다.")
    }

}
