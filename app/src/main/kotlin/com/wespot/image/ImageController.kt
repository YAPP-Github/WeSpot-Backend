package com.wespot.image

import com.wespot.image.dto.ImageResponse
import com.wespot.image.dto.PresignedResponse
import com.wespot.image.`in`.ImageUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/image")
class ImageController(
    private val imageUseCase: ImageUseCase,
) {

    @GetMapping("/presigned-url")
    fun getBucketDomain(imageExtension: String): ResponseEntity<PresignedResponse> {
        val tenMinute = 10L
        val createPresignedUrl = imageUseCase.createPresignedUrl(imageExtension, tenMinute)

        return ResponseEntity.ok(createPresignedUrl)
    }

    @PostMapping("/update-profile")
    fun saveImageProfile(url: String?): ResponseEntity<ImageResponse> {
        val response = imageUseCase.saveWithUpdateProfile(url)

        return ResponseEntity.ok(response)
    }

}
