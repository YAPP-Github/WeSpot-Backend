package com.wespot.post.port.out

import com.wespot.post.Post

interface PostPort {

    fun save(post: Post): Post

    fun searchByTitle(title: String, viewerId: Long? = null): List<Post>

    fun searchByDescription(description: String, viewerId: Long? = null): List<Post>

    fun findById(postId: Long, viewerId: Long? = null): Post?

    fun findAllByCategoryId(categoryId: Long, viewerId: Long? = null): List<Post>

    fun findAllByCategoryIdIn(
        categoryIds: List<Long>,
        viewerId: Long? = null
    ): List<Post>

    fun findAllByUserId(authorId: Long): List<Post>

    fun findAllByPostIdIn(postIds: List<Long>, viewerId: Long? = null): List<Post>

    fun findAllRecentPostByLimit(
        limit: Int,
        viewerId: Long? = null
    ): List<Post>

}
