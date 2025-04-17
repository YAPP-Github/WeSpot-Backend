package com.wespot.image.service.listener

import com.wespot.image.event.CreatedImageWhenSignUpEvent
import com.wespot.image.event.DeletedImageEvent
import com.wespot.image.event.SavedImageEvent
import com.wespot.image.out.ImagePort
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ImageEventListener(
    private val imagePort: ImagePort
) {

    @EventListener
    fun signUpAndImageUpload(event: CreatedImageWhenSignUpEvent) {
        imagePort.save(event.image)
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    fun saveImage(event: SavedImageEvent) {
        imagePort.save(event.image)
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    fun deleteImage(event: DeletedImageEvent) {
        imagePort.deleteByUrl(event.url)
    }

}
