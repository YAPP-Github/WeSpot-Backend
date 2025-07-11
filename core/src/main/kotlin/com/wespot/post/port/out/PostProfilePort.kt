package com.wespot.post.port.out

import com.wespot.post.PostProfile

interface PostProfilePort {

    fun findByUserId(userId: Long): PostProfile?

    fun save(postProfile: PostProfile): PostProfile

    fun findByUserIdIn(userIds: List<Long>): List<PostProfile>

}
