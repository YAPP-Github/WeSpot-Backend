package com.wespot.user.adapter

import com.wespot.user.mapper.RestrictionMapper
import com.wespot.user.port.out.RestrictionPort
import com.wespot.user.repository.RestrictionJpaRepository
import com.wespot.user.restriction.Restriction
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class RestrictionPersistenceAdapter(
    private val restrictionJpaRepository: RestrictionJpaRepository
) : RestrictionPort {
    override fun findById(id: Long): Restriction? {
        return restrictionJpaRepository.findByIdOrNull(id)
            ?.let { RestrictionMapper.mapToDomainEntity(it) }
    }

    override fun save(restriction: Restriction): Restriction {
        return restrictionJpaRepository.save(RestrictionMapper.mapToJpaEntity(restriction))
            .let { RestrictionMapper.mapToDomainEntity(it) }
    }

}
