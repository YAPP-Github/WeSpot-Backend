package com.wespot.common.`in`

import com.wespot.common.dto.UpdatedModalComponentResponse
import com.wespot.notification.PublishNotificationType

interface UpdatedFeatureUseCase {

    fun getUpdatedFeatureScreen(publishNotificationType: PublishNotificationType): UpdatedModalComponentResponse

}
