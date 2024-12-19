package com.wespot.image

import com.wespot.common.BaseEntity

object ImageMapper {

    fun mapToDomainEntity(imageJpaEntity: ImageJpaEntity): Image =
        Image(
            imageJpaEntity.id,
            imageJpaEntity.url,
            imageJpaEntity.baseEntity.createdAt
        )

    fun mapToJpaEntity(image: Image): ImageJpaEntity =
        ImageJpaEntity(
            image.id,
            image.url,
            BaseEntity(
                createdAt = image.createdAt,
                updatedAt = image.createdAt
            )
        )

}
