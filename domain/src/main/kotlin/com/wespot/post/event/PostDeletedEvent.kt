package com.wespot.post.event

import com.wespot.post.Post

class PostDeletedEvent(
    val deleterId: Long,
    val deletedPost: Post,
) {
}
