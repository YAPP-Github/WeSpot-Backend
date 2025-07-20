package com.wespot.post.port.out

import com.wespot.post.PostReport

interface PostReportPort {

    fun findByPostIdAndUserId(postId: Long, userId: Long): PostReport?

    fun deleteById(id: Long)

    fun save(postBlock: PostReport): PostReport

    fun deleteByPostId(postId: Long)

}
