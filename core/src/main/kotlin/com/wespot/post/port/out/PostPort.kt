package com.wespot.post.port.out

import com.wespot.post.Post

interface PostPort {

    fun save(post: Post): Post
    fun searchByTitle(title: String):List<Post>

    fun searchByDescription(description: String):List<Post>

}
