package com.wespot.post.port.`in`

import com.wespot.common.dto.PostPagingResponse

interface PostSearchedUseCase {

    fun search(
        keyword: String,
        inquirySize: Long,
        cursorId: Long?
    ): PostPagingResponse

}
