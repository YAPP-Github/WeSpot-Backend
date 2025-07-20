package com.wespot.comment.port.out

import com.wespot.comment.PostComment

interface PostCommentPort {

    fun save(postComment: PostComment): PostComment

    fun findAllByPostId(postId: Long, viewerId: Long? = null): List<PostComment>

    fun findAllByUserId(userId: Long, viewerId: Long? = null): List<PostComment>

    fun findById(id: Long, viewerId: Long? = null): PostComment?

    fun deleteByPostId(postId: Long)

}
