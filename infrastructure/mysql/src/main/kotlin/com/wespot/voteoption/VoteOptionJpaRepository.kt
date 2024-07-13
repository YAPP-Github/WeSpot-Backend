package com.wespot.voteoption

import org.springframework.data.jpa.repository.JpaRepository

interface VoteOptionJpaRepository : JpaRepository<VoteOptionJpaEntity, Long> {
}