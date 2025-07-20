package com.wespot.comment.port.out

import com.wespot.comment.PostCommentLike

interface PostCommentLikePort {

    fun isExistsByPostCommentIdAndUserId(postCommentId: Long, userId: Long): Boolean

    fun findAllByPostCommentIdInAndUserId(postCommentIds: List<Long>, userId: Long): List<PostCommentLike>

    fun findByPostCommentIdAndUserId(postCommentId: Long, userId: Long): PostCommentLike?

    fun save(postCommentLike: PostCommentLike): PostCommentLike

    fun deleteById(id: Long)

    fun deleteByPostCommentIdIn(postCommentIds: List<Long>)

}
