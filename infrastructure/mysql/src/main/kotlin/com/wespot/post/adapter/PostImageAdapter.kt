package com.wespot.post.adapter

import com.wespot.post.port.out.PostImagePort
import com.wespot.post.repository.PostImageJpaRepository
import org.springframework.stereotype.Repository

@Repository
class PostImageAdapter(
    private val postImageJpaRepository: PostImageJpaRepository
) : PostImagePort {

    override fun deleteByPostId(postId: Long) {
        postImageJpaRepository.deleteByPostId(postId)
    }

}
