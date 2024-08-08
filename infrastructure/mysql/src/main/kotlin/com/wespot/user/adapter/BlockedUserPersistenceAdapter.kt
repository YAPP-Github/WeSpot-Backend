package com.wespot.user.adapter

import com.wespot.user.block.BlockedUser
import com.wespot.user.mapper.BlockedUserMapper
import com.wespot.user.port.out.BlockedUserPort
import com.wespot.user.repository.BlockedUserJpaRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class BlockedUserPersistenceAdapter(
    private val blockedUserJpaRepository: BlockedUserJpaRepository
): BlockedUserPort {
    override fun existsByBlockerIdAndBlockedId(blockerId: Long, blockedId: Long): Boolean {
        return blockedUserJpaRepository.existsByBlockerIdAndBlockedId(blockerId, blockedId)
    }

    override fun existsByBlockerIdAndBlockedIdAndMessageId(blockerId: Long, blockedId: Long, messageId: Long): Boolean {
        return blockedUserJpaRepository.existsByBlockerIdAndBlockedIdAndMessageId(blockerId, blockedId, messageId)
    }

    override fun deleteByBlockerIdAndBlockedIdAndMessageId(blockerId: Long, blockedId: Long, messageId: Long) {
        blockedUserJpaRepository.deleteByBlockerIdAndBlockedIdAndMessageId(blockerId, blockedId, messageId)
    }

    override fun save(blockedUser: BlockedUser): BlockedUser {
        return blockedUserJpaRepository.save(BlockedUserMapper.mapToJpaEntity(blockedUser))
            .let { BlockedUserMapper.mapToDomainEntity(it) }
    }

    override fun findAllByBlockerIdAfterCursor(blockerId: Long, cursorId: Long, pageable: Pageable): List<BlockedUser> {
        return blockedUserJpaRepository.findAllByBlockerIdAfterCursor(blockerId, cursorId, pageable)
            .map { BlockedUserMapper.mapToDomainEntity(it) }
            .toList()
    }

    override fun countBlockedUsersAfterCursor(blockerId: Long, cursorId: Long, pageable: Pageable): Long {
        return blockedUserJpaRepository.countBlockedUsersAfterCursor(blockerId, cursorId)
    }

    override fun findAllByBlockerId(blockerId: Long): List<BlockedUser> {
        return blockedUserJpaRepository.findAllByBlockerId(blockerId)
            .map { BlockedUserMapper.mapToDomainEntity(it) }
            .toList()
    }

}
