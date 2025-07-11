package com.wespot.comment.port.out

import com.wespot.comment.PostComment

interface PostCommentPort {

    fun save(postComment: PostComment): PostComment

    fun findAllByPostId(postId: Long): List<PostComment>

}
