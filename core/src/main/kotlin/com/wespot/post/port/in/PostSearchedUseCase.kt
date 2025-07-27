package com.wespot.post.port.`in`

import com.wespot.post.dto.response.PostComponentResponse

interface PostSearchedUseCase {

    fun search(
        keyword: String,
    ): List<PostComponentResponse>

}
