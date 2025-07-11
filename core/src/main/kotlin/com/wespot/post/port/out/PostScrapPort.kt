package com.wespot.post.port.out

import com.wespot.post.PostScrap

interface PostScrapPort {

    fun findAllByUserId(userId: Long): List<PostScrap>

    fun deleteById(id: Long)

    fun save(postScrap: PostScrap): PostScrap

    fun findByPostIdAndUserId(postId: Long, userId: Long): PostScrap?

}
