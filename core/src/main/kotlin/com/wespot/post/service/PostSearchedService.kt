package com.wespot.post.service

import com.wespot.post.dto.response.PostComponentResponse
import com.wespot.post.port.`in`.PostSearchedUseCase
import com.wespot.post.port.out.PostPort
import com.wespot.post.server_driven.PostComponent
import com.wespot.post.vo.PostSearchKeyword
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostSearchedService(
    private val postPort: PostPort
) : PostSearchedUseCase {

    @Transactional(readOnly = true)
    override fun search(
        keyword: String,
    ): List<PostComponentResponse> {
        val postSearchKeyword = PostSearchKeyword.from(keyword = keyword)
        val keywordsToSearch = postSearchKeyword.keywordsToSearch()
        val postsByTitle =
            keywordsToSearch.map { postPort.searchByTitle(it) }
                .flatten()
        val postsByDescription =
            keywordsToSearch.map { postPort.searchByDescription(it) }
                .flatten()

        return (postsByTitle + postsByDescription)
            .asSequence()
            .distinct()
            .sortedByDescending { it.createdAt }
            .distinct()
            .map { PostComponent.from(it) }
            .map { PostComponentResponse.from(it) }
            .toList()
    }
}
