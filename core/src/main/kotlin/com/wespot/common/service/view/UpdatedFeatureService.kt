package com.wespot.common.service.view

import com.wespot.common.dto.UpdatedModalComponentResponse
import com.wespot.common.`in`.UpdatedFeatureUseCase
import com.wespot.common.view.update.UpdatedModalComponent
import com.wespot.notification.PublishNotificationType
import org.springframework.stereotype.Service

@Service
class UpdatedFeatureService : UpdatedFeatureUseCase {

    override fun getUpdatedFeatureScreen(publishNotificationType: PublishNotificationType): UpdatedModalComponentResponse {
        val updateModalComponent =
            UpdatedModalComponent.fromWithNotificationType(publishNotificationType.updatedUserNotificationType)

        return UpdatedModalComponentResponse.from(updateModalComponent)
    }

}
