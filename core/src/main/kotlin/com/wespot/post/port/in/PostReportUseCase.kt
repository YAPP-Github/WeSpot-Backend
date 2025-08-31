package com.wespot.post.port.`in`

import com.wespot.post.dto.request.PostReportRequest

interface PostReportUseCase {

    fun reportPost(postId: Long, postReportRequest: PostReportRequest?)

}
