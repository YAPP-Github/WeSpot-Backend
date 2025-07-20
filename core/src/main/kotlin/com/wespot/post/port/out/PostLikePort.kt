package com.wespot.post.port.out

import com.wespot.post.PostLike
import com.wespot.post.port.`in`.PostLikeUseCase

interface PostLikePort {

    fun deleteById(id: Long)

    fun save(postLike: PostLike): PostLike

    fun findByPostIdAndUserId(postId: Long, userId: Long): PostLike?

    fun deleteByPostId(postId: Long)

}

