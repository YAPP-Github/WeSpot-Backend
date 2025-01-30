package com.wespot.image.service

import com.wespot.image.Image
import com.wespot.image.dto.ImageResponse
import com.wespot.image.dto.PresignedResponse
import com.wespot.image.dto.ProfileUpdateRequest
import com.wespot.image.`in`.ImageUseCase
import com.wespot.image.out.ImagePort
import com.wespot.image.out.S3Port
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ImageService(
    private val s3Port: S3Port,
    private val imagePort: ImagePort,
    @Value("\${aws.cloud-front.url}")
    private val cloudFrontUrl: String,
) : ImageUseCase {

    override fun createPresignedUrl(imageExtension: String, expirationTime: Long): PresignedResponse {
        val prefix = UUID.randomUUID()
            .toString()
            .replace("-", "")
        val imageName = "$prefix.$imageExtension"
        val url = s3Port.getPresignedUrl(imageName, expirationTime)

        return PresignedResponse(url, imageName)
    }

    @Transactional
    override fun saveWithUpdateProfile(request: ProfileUpdateRequest): ImageResponse {
        val savedImage = Image.ofWithUpdateProfile(
            request.introduction,
            request.url,
            cloudFrontUrl
        ) { image -> imagePort.save(image) }

        return ImageResponse.of(savedImage.id, savedImage)
    }

}
