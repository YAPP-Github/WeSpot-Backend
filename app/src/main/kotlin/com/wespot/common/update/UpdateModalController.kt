package com.wespot.common.update

import com.wespot.common.dto.UpdatedModalComponentResponse
import com.wespot.common.`in`.UpdatedFeatureUseCase
import com.wespot.common.update.swagger.UpdateModalSwagger
import com.wespot.notification.PublishNotificationType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/update-modal")
class UpdateModalController(
    private val updatedFeatureUseCase: UpdatedFeatureUseCase
) : UpdateModalSwagger {

    @GetMapping
    override fun getProfileUpdateScreen(publishNotificationType: PublishNotificationType): ResponseEntity<UpdatedModalComponentResponse> {
        val response = updatedFeatureUseCase.getUpdatedFeatureScreen(publishNotificationType)

        return ResponseEntity.ok(response)
    }

}
