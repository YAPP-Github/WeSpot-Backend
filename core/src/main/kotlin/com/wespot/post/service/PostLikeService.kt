package com.wespot.post.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.exception.CustomException
import com.wespot.post.PostLike
import com.wespot.post.port.`in`.PostLikeUseCase
import com.wespot.post.port.out.PostLikePort
import com.wespot.post.port.out.PostPort
import com.wespot.user.port.out.UserPort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostLikeService(
    private val userPort: UserPort,
    private val postPort: PostPort,
    private val postLikePort: PostLikePort
) : PostLikeUseCase {

    @Transactional
    override fun likePost(postId: Long) {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)

        if (loginUser.canNotUseCommunity()) {
            throw CustomException(status = HttpStatus.FORBIDDEN, message = "커뮤니티 이용이 제한된 사용자입니다.")
        }

        val post = postPort.findById(postId) ?: throw CustomException(message = "존재하지 않는 게시글입니다.")
        postLikePort.findByPostIdAndUserId(postId = postId, userId = loginUser.id)
            ?.let {
                postLikePort.deleteById(it.id)
                val removedLikePost = post.removeLike()
                postPort.save(removedLikePost)
            }
            ?: run {
                val postLike = PostLike(postId = postId, userId = loginUser.id)
                postLikePort.save(postLike)
                val addedLikePost = post.addLike()
                postPort.save(addedLikePost)
            }
    }

}
