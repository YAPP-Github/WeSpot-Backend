package com.wespot.post.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.common.dto.PostPagingResponse
import com.wespot.post.dto.response.PostComponentResponse
import com.wespot.post.port.`in`.PostSearchedUseCase
import com.wespot.post.port.out.PostBlockPort
import com.wespot.post.port.out.PostPort
import com.wespot.post.server_driven.PostComponent
import com.wespot.post.vo.PostSearchKeyword
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostSearchedService(
    private val postPort: PostPort,
    private val userPort: UserPort,
    private val postBlockPort: PostBlockPort,
) : PostSearchedUseCase {

    @Transactional(readOnly = true)
    override fun search(
        keyword: String,
        inquirySize: Long,
        cursorId: Long?
    ): PostPagingResponse {
        val loginUser = SecurityUtils.getLoginUser(userPort = userPort)
        val postSearchKeyword = PostSearchKeyword.from(keyword = keyword)
        val keywordsToSearch = postSearchKeyword.keywordsToSearchInRegex()
        val posts = postPort.searchByTitleAndDescription(
            keyword = keywordsToSearch,
            viewerId = loginUser.id,
            inquirySize = inquirySize + 1,
            cursorId = cursorId,
            blockPostIds = postBlockPort.findAllByUserId(loginUser.id).map { it.postId }
        )

        val data = posts.map { PostComponent.of(it, loginUser.id) }.map { PostComponentResponse.from(it) }
        val hasNext = posts.size == (inquirySize + 1).toInt()
        val lastCursorId = posts.minOfOrNull { it.id }

        return PostPagingResponse(
            data = data.take(inquirySize.toInt()),
            hasNext = hasNext,
            lastCursorId = lastCursorId
        )
    }
}
