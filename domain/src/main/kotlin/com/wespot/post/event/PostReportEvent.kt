package com.wespot.post.event

import com.wespot.post.Post
import com.wespot.user.User

data class PostReportEvent(
    val sender: User,
    val receiver: User,
    val reason: String,
    val post: Post
) {
}
