package com.wespot.auth

import org.springframework.data.jpa.repository.JpaRepository

interface PersonalInfoJpaRepository: JpaRepository<PersonalInfoJpaEntity, Long> {

    fun findByEmail(email: String): PersonalInfoJpaEntity?

    fun findBySocialId(socialId: String): PersonalInfoJpaEntity?

}
