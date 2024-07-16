package com.wespot.user.adapter

import com.wespot.user.User
import com.wespot.user.repository.UserJpaRepository
import com.wespot.user.mapper.UserMapper
import com.wespot.user.port.out.UserPort
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Repository
class UserPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository,
) : UserPort {

    override fun findByEmail(userEmail: String): User? {
        return userJpaRepository.findByEmail(userEmail)
            ?.let { UserMapper.mapToDomainEntity(it) }
    }

    override fun save(user: User): User {
        return userJpaRepository.save(UserMapper.mapToJpaEntity(user))
            .let { UserMapper.mapToDomainEntity(it) }
    }

    override fun findById(userId: Long): User? {
         return userJpaRepository.findById(userId)
             .orElseThrow { NoSuchElementException("유저를 찾을 수 없습니다.") }
             .let { UserMapper.mapToDomainEntity(it) }
    }

}