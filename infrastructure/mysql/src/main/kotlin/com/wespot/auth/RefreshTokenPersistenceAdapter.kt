package com.wespot.auth

import com.wespot.auth.port.out.RefreshTokenPort
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.school.SchoolJpaEntity
import com.wespot.school.SchoolJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class RefreshTokenPersistenceAdapter(
    private val refreshTokenJpaRepository: RefreshTokenJpaRepository,
    private val schoolJpaRepository: SchoolJpaRepository
) : RefreshTokenPort {


    @Transactional
    override fun save(refreshToken: RefreshToken): RefreshToken {
        return RefreshTokenMapper.mapToJpaEntity(refreshToken)
            .let { refreshTokenJpaRepository.save(it) }
            .let {
                val school = getSchool(it)
                RefreshTokenMapper.mapToDomainEntity(it, schoolJpaEntity = school)
            }
    }

    private fun getSchool(it: RefreshTokenJpaEntity): SchoolJpaEntity {
        val school = schoolJpaRepository.findByIdOrNull(it.user.schoolId) ?: throw CustomException(
            message = "학교를 찾을 수 없습니다.",
            view = ExceptionView.TOAST,
            status = HttpStatus.BAD_REQUEST,
        )
        return school
    }

    override fun findByUserId(userId: Long): RefreshToken? {
        return refreshTokenJpaRepository.findByUserId(userId)
            ?.let { RefreshTokenMapper.mapToDomainEntity(it, schoolJpaEntity = getSchool(it)) }
    }

    @Transactional
    override fun saveOrUpdate(refreshToken: RefreshToken): RefreshToken {
        val refreshTokenJpaEntity = refreshTokenJpaRepository.findByUserId(refreshToken.user.id)
        return if (refreshTokenJpaEntity != null) {
            refreshTokenJpaEntity.update(refreshToken.refreshToken)
            refreshTokenJpaRepository.save(refreshTokenJpaEntity)
                .let { RefreshTokenMapper.mapToDomainEntity(it, getSchool(it)) }
        } else {
            save(refreshToken)
        }
    }

    @Transactional
    override fun deleteByUserId(userId: Long) {
        refreshTokenJpaRepository.deleteByUserId(userId)
    }

    override fun findByRefreshToken(refreshToken: String): RefreshToken? {
        val findByRefreshToken = refreshTokenJpaRepository.findByRefreshToken(refreshToken)
        if (findByRefreshToken != null) {
            return RefreshTokenMapper.mapToDomainEntity(findByRefreshToken, getSchool(findByRefreshToken))
        }
        return null
    }

}
