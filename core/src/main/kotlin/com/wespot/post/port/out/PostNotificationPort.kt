package com.wespot.post.port.out

import com.wespot.post.PostNotification

interface PostNotificationPort {

    fun findAllByPostId(postId: Long): List<PostNotification>

    fun save(of: PostNotification): PostNotification

    fun findByPostIdAndUserId(postId: Long, userId: Long): PostNotification?

    fun deleteById(id: Long)

}
