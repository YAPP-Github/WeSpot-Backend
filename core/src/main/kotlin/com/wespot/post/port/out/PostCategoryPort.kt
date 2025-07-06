package com.wespot.post.port.out

import com.wespot.post.PostCategory

interface PostCategoryPort {

    fun findAll(): List<PostCategory>

}
