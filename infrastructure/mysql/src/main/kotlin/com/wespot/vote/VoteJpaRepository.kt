package com.wespot.vote

import org.springframework.data.jpa.repository.JpaRepository

interface VoteJpaRepository : JpaRepository<VoteJpaEntity, Long> {
}