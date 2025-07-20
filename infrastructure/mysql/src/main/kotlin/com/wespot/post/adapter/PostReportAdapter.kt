package com.wespot.post.adapter

import com.wespot.post.PostReport
import com.wespot.post.mapper.PostReportMapper
import com.wespot.post.port.out.PostReportPort
import com.wespot.post.repository.PostReportJpaRepository
import org.springframework.stereotype.Repository

@Repository
class PostReportAdapter(
    private val postReportJpaRepository: PostReportJpaRepository
) : PostReportPort {

    override fun findByPostIdAndUserId(postId: Long, userId: Long): PostReport? {
        return postReportJpaRepository.findByPostIdAndUserId(postId, userId)?.let { PostReportMapper.toDomain(it) }
    }

    override fun deleteById(id: Long) {
        postReportJpaRepository.deleteById(id)
    }

    override fun save(postBlock: PostReport): PostReport {
        val postBlockEntity = PostReportMapper.toEntity(postBlock)
        val savedPostBlockEntity = postReportJpaRepository.save(postBlockEntity)
        return PostReportMapper.toDomain(savedPostBlockEntity)
    }

    override fun deleteByPostId(postId: Long) {
        postReportJpaRepository.deleteByPostId(postId)
    }

}
