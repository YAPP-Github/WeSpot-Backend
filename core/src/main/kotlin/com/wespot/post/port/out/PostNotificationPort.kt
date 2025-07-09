package com.wespot.post.port.out

import com.wespot.post.PostNotification

interface PostNotificationPort {

    fun findAllByPostId(postId: Long): List<PostNotification>

}
