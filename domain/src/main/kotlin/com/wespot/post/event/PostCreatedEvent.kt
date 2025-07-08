package com.wespot.post.event

import com.wespot.post.Post

data class PostCreatedEvent(
    val post: Post
) {

}
