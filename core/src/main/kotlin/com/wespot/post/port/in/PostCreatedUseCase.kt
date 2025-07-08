package com.wespot.post.port.`in`

import com.wespot.post.dto.request.CreatedPostRequest

interface PostCreatedUseCase {

    fun createPost(createdPostRequest: CreatedPostRequest): Long

}
