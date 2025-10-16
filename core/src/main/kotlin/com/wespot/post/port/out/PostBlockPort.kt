package com.wespot.post.port.out

import com.wespot.post.PostBlock

interface PostBlockPort {

    fun findByPostIdAndUserId(postId: Long, userId: Long): PostBlock?

    fun deleteById(id: Long)

    fun save(postBlock: PostBlock): PostBlock

    fun deleteByPostId(postId: Long)

    fun findAllByUserId(userId: Long): List<PostBlock>

}
