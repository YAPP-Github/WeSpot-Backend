package com.wespot.image

import org.springframework.data.jpa.repository.JpaRepository

interface ImageJpaRepository : JpaRepository<ImageJpaEntity, Long> {

    fun deleteByUrl(url: String)

}
