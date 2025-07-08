package com.wespot.post.port.out

import com.wespot.post.Post

interface PostPort {

    fun save(post: Post): Post

}
