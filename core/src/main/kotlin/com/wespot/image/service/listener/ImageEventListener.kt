package com.wespot.image.service.listener

import com.wespot.image.event.CreatedImageWhenSignUpEvent
import com.wespot.image.out.ImagePort
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class ImageEventListener(
    private val imagePort: ImagePort
) {

    @EventListener
    fun signUpAndImageUpload(event: CreatedImageWhenSignUpEvent) {
        imagePort.save(event.image)
    }

}
