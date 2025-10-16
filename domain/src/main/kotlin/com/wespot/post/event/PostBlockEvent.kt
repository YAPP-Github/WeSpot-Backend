package com.wespot.post.event

import com.wespot.post.Post
import com.wespot.user.User

data class PostBlockEvent(
    val sender: User,
    val receiver: User,
    val post: Post
) {
}
