package com.wespot.post.port.out

import com.wespot.post.Post

interface PostPort {

    fun save(post: Post): Post

    fun searchByTitleAndDescription(
        keyword: String,
        viewerId: Long? = null,
        inquirySize: Long,
        blockPostIds: List<Long>,
        cursorId: Long?
    ): List<Post>

    fun searchByTitle(
        title: String, viewerId: Long? = null, blockPostIds: List<Long>,
    ): List<Post>

    fun searchByDescription(
        description: String, viewerId: Long? = null, blockPostIds: List<Long>,
    ): List<Post>

    fun findById(
        postId: Long, viewerId: Long? = null,
    ): Post?

    fun findAllByCategoryId(
        categoryId: Long, viewerId: Long? = null, inquirySize: Long, cursorId: Long?, blockPostIds: List<Long>,
    ): List<Post>

    fun findAllByCategoryIdIn(
        categoryIds: List<Long>,
        viewerId: Long? = null,
        inquirySize: Long,
        blockPostIds: List<Long>,
        cursorId: Long?
    ): List<Post>

    fun findAllByUserId(
        authorId: Long, inquirySize: Long, cursorId: Long?, blockPostIds: List<Long>,
    ): List<Post>

    fun findAllByPostIdIn(
        postIds: List<Long>,
        viewerId: Long? = null,
        inquirySize: Long,
        blockPostIds: List<Long>,
        cursorId: Long?
    ): List<Post>

    fun findAllRecentPost(
        viewerId: Long? = null,
        inquirySize: Long,
        blockPostIds: List<Long>,
        cursorId: Long?
    ): List<Post>

    fun deleteById(id: Long)

}
