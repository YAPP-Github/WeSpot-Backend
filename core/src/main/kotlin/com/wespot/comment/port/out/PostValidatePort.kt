package com.wespot.comment.port.out

interface PostValidatePort {

    fun existsPostById(postId: Long): Boolean

}
