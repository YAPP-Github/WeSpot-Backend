package com.wespot.post.adapter

import com.wespot.post.PostNotification
import com.wespot.post.PostNotificationEntity
import com.wespot.post.mapper.PostNotificationMapper
import com.wespot.post.port.out.PostNotificationPort
import com.wespot.post.repository.PostNotificationJpaRepository
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Repository

@Repository
class PostNotificationAdapter(
    private val userPort: UserPort,
    private val postNotificationJpaRepository: PostNotificationJpaRepository
) : PostNotificationPort {

    override fun findAllByPostId(postId: Long): List<PostNotification> {
        val postNotificationEntities = postNotificationJpaRepository.findAllByPostId(postId)

        return getCompletePostNotifications(postNotificationEntities)
    }

    private fun getCompletePostNotifications(postNotificationEntities: List<PostNotificationEntity>): List<PostNotification> {
        val userIds = postNotificationEntities.map { it.userId }.distinct()
        val userIdToUser = userPort.findAllByIdIn(userIds)
            .associateBy { it.id }

        return postNotificationEntities.map { PostNotificationMapper.toDomain(it, userIdToUser[it.userId]!!) }
    }

    override fun save(postNotification: PostNotification): PostNotification {
        val postNotificationEntity = PostNotificationMapper.toEntity(postNotification)
        val savedPostNotificationEntity = postNotificationJpaRepository.save(postNotificationEntity)

        return PostNotificationMapper.toDomain(entity = savedPostNotificationEntity, user = postNotification.user)
    }

    override fun findByPostIdAndUserId(postId: Long, userId: Long): PostNotification? {
        return postNotificationJpaRepository.findByPostIdAndUserId(postId = postId, userId = userId)
            ?.let { PostNotificationMapper.toDomain(it, userPort.findById(userId)!!) }
    }

    override fun deleteById(id: Long) {
        postNotificationJpaRepository.deleteById(id)
    }

    override fun deleteByPostId(postId: Long) {
        postNotificationJpaRepository.deleteByPostId(postId)
    }

}
