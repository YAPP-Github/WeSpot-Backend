package com.wespot.post.port.`in`

import com.wespot.post.dto.response.PostResponse

interface PostSearchedUseCase {

    fun search(keyword: String): List<PostResponse>

}
