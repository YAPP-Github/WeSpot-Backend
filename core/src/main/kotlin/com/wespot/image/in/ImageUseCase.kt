package com.wespot.image.`in`

import com.wespot.image.dto.ImageResponse
import com.wespot.image.dto.PresignedResponse

interface ImageUseCase {

    fun createPresignedUrl(imageExtension: String, expirationTime: Long): PresignedResponse

    fun saveWithUpdateProfile(url: String?): ImageResponse

}
