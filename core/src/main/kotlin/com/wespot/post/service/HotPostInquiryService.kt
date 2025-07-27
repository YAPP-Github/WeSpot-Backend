package com.wespot.post.service

import com.wespot.post.Post
import com.wespot.post.port.`in`.HotPostInquiryUseCase
import com.wespot.post.port.out.PostPort
import com.wespot.user.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HotPostInquiryService(
    private val postPort: PostPort,
) : HotPostInquiryUseCase {

    @Transactional(readOnly = true)
    override fun topPost(user: User, countOfView: Int): List<Post> {
        val countOfPostToStatistic = 100L
        val posts =
            postPort.findAllRecentPostByLimit(inquirySize = countOfPostToStatistic, viewerId = user.id, cursorId = null)
        return posts
            .sortedByDescending { it.createdAt }
            .sortedByDescending { it.scoreOfPost() }
            .take(countOfView)
    }
}
