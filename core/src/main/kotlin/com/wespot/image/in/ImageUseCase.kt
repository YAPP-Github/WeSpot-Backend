package com.wespot.image.`in`

import com.wespot.image.dto.ImageResponse
import com.wespot.image.dto.PresignedResponse
import com.wespot.image.dto.ProfileUpdateRequest

interface ImageUseCase {

    fun createPresignedUrl(imageExtension: String, expirationTime: Long): PresignedResponse

    fun saveWithUpdateProfile(request: ProfileUpdateRequest): ImageResponse

}
