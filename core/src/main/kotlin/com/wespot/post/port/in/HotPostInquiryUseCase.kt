package com.wespot.post.port.`in`

import com.wespot.post.Post
import com.wespot.user.User

interface HotPostInquiryUseCase {

    fun topPost(user: User, countOfView: Int): List<Post>

}
