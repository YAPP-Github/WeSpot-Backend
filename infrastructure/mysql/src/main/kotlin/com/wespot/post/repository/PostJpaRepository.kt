package com.wespot.post.repository

import com.wespot.post.PostEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PostJpaRepository : JpaRepository<PostEntity, Long> {

    fun findByTitleContainingAndIdNotInOrderByBaseEntityCreatedAtDesc(
        title: String,
        blockPostIds: List<Long>,
    ): List<PostEntity>

    fun findAllByDescriptionContainingAndIdNotInOrderByBaseEntityCreatedAtDesc(
        description: String,
        @Param("ids") blockPostIds: List<Long>,
    ): List<PostEntity>

    fun findByCategoryIdAndIdNotInAndIdLessThanOrderByBaseEntityCreatedAtDesc(
        categoryId: Long,
        @Param("ids") blockPostIds: List<Long>,
        cursorId: Long,
        pageable: Pageable
    ): List<PostEntity>

    fun findByCategoryIdInAndIdNotInAndIdLessThanOrderByBaseEntityCreatedAtDesc(
        categoryIds: List<Long>,
        @Param("ids") blockPostIds: List<Long>,
        cursorId: Long,
        pageable: Pageable
    ): List<PostEntity>

    fun findAllByUserIdAndIdNotInAndIdLessThanOrderByBaseEntityCreatedAtDesc(
        userId: Long,
        @Param("ids") blockPostIds: List<Long>,
        cursorId: Long,
        pageable: Pageable
    ): List<PostEntity>

    fun findAllByIdInAndIdNotInAndIdLessThanOrderByBaseEntityCreatedAtDesc(
        postIds: List<Long>,
        @Param("ids") blockPostIds: List<Long>,
        cursorId: Long,
        pageable: Pageable
    ): List<PostEntity>

    fun findAllByIdNotInAndIdLessThanOrderByBaseEntityCreatedAtDesc(
        @Param("ids") blockPostIds: List<Long>,
        cursorId: Long,
        pageable: Pageable
    ): List<PostEntity>

    @Query(
        value = """
            SELECT *
            FROM post
            WHERE (title REGEXP :pattern
                   OR description REGEXP :pattern)
              AND id < :cursorId
              AND id NOT IN :blockPostIds
            ORDER BY created_at DESC
            LIMIT :limit
        """,
        nativeQuery = true
    )
    fun searchByTitleAndDescription(
        pattern: String,
        blockPostIds: List<Long>,
        cursorId: Long,
        limit: Int,
    ): List<PostEntity>

    @Modifying(clearAutomatically = true)
    @Query("UPDATE PostEntity p SET p.userId = :toUserId WHERE p.userId = :fromUserId")
    fun reassignUserId(@Param("fromUserId") fromUserId: Long, @Param("toUserId") toUserId: Long)

}
