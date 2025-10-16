package com.wespot.post.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.exception.CustomException
import com.wespot.post.PostScrap
import com.wespot.post.port.`in`.PostScrapUseCase
import com.wespot.post.port.out.PostPort
import com.wespot.post.port.out.PostScrapPort
import com.wespot.user.port.out.UserPort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostScrapService(
    private val userPort: UserPort,
    private val postScrapPort: PostScrapPort,
    private val postPort: PostPort
) : PostScrapUseCase {

    @Transactional
    override fun scrapPost(postId: Long) {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)

        if (loginUser.canNotUseCommunity()) {
            throw CustomException(status = HttpStatus.FORBIDDEN, message = "커뮤니티 이용이 제한된 사용자입니다.")
        }

        val post = postPort.findById(postId) ?: throw CustomException(message = "존재하지 않는 게시글입니다.")
        postScrapPort.findByPostIdAndUserId(postId = post.id, userId = loginUser.id)
            ?.let {
                postScrapPort.deleteById(it.id)
                val deleteBookmark = post.deleteBookmark()
                postPort.save(deleteBookmark)
            }
            ?: run {
                val postScrap = PostScrap(postId = post.id, userId = loginUser.id)
                postScrapPort.save(postScrap)
                val addBookmark = post.addBookmark()
                postPort.save(addBookmark)
            }
    }

}
