package com.wespot.comment.repository

import com.wespot.comment.PostCommentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PostCommentJpaRepository : JpaRepository<PostCommentEntity, Long> {

    fun findAllByPostId(postId: Long): List<PostCommentEntity>

    fun findAllByUserId(userId: Long): List<PostCommentEntity>

    fun deleteByPostId(postId: Long)

    @Modifying(clearAutomatically = true)
    @Query("UPDATE PostCommentEntity p SET p.userId = :toUserId WHERE p.userId = :fromUserId")
    fun reassignUserId(@Param("fromUserId") fromUserId: Long, @Param("toUserId") toUserId: Long)

}
