package com.wespot.user.adapter

import com.wespot.user.block.BlockedUser
import com.wespot.user.mapper.BlockedUserMapper
import com.wespot.user.port.out.BlockedUserPort
import com.wespot.user.repository.BlockedUserJpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class BlockedUserPersistenceAdapter(
    private val blockedUserJpaRepository: BlockedUserJpaRepository
): BlockedUserPort {

    override fun existsByBlockerIdAndBlockedId(blockerId: Long, blockedId: Long): Boolean {
        return blockedUserJpaRepository.existsByBlockerIdAndBlockedId(blockerId, blockedId)
    }

    override fun deleteByBlockerIdAndBlockedId(blockerId: Long, blockedId: Long) {
        blockedUserJpaRepository.deleteByBlockerIdAndBlockedId(blockerId, blockedId)
    }

    override fun save(blockedUser: BlockedUser): BlockedUser {
        return blockedUserJpaRepository.save(BlockedUserMapper.mapToJpaEntity(blockedUser))
            .let { BlockedUserMapper.mapToDomainEntity(it) }
    }

    override fun findAllByBlockerId(blockerId: Long): List<BlockedUser> {
        return blockedUserJpaRepository.findAllByBlockerId(blockerId)
            .map { BlockedUserMapper.mapToDomainEntity(it) }
            .toList()
    }

    override fun findBlockedTime(blockerId: Long, blockedId: Long): LocalDateTime? {
        return blockedUserJpaRepository.findBlockedTime(blockerId, blockedId)
    }

}
