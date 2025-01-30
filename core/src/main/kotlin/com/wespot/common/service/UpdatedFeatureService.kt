package com.wespot.common.service

import com.wespot.common.dto.UpdatedModalComponentResponse
import com.wespot.common.`in`.UpdatedFeatureUseCase
import com.wespot.common.view.UpdatedModalComponent
import com.wespot.notification.NotificationType
import com.wespot.notification.PublishNotificationType
import org.springframework.stereotype.Service

@Service
class UpdatedFeatureService : UpdatedFeatureUseCase {

    override fun getUpdatedFeatureScreen(publishNotificationType: PublishNotificationType): UpdatedModalComponentResponse {
        val component =
            UpdatedModalComponent.fromWithNotificationType(publishNotificationType.updatedUserNotificationType)

        return UpdatedModalComponentResponse.from(component)
    }

}
