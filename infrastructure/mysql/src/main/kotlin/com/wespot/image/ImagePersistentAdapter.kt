package com.wespot.image

import com.wespot.image.out.ImagePort
import org.springframework.stereotype.Repository

@Repository
class ImagePersistentAdapter(
    private val imageJpaRepository: ImageJpaRepository
) : ImagePort {

    override fun save(image: Image): Image {
        val imageJpaEntity = ImageMapper.mapToJpaEntity(image)
        val savedImage = imageJpaRepository.save(imageJpaEntity)
        return ImageMapper.mapToDomainEntity(savedImage)
    }

    override fun deleteByUrl(url: String) {
        imageJpaRepository.deleteByUrl(url)
    }

}
