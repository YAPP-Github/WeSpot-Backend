package com.wespot.common.view

import com.wespot.common.NotificationTypeToUpdateFeatureContent
import com.wespot.notification.NotificationType

data class UpdatedModalComponent(
    val type: String,
    val textComponent: TextComponent,
    val imageComponent: ImageComponent,
    val skipButtonComponent: ButtonComponent,
    val moveToUpdatedFeatureViewButtonComponent: ButtonComponent
) {

    companion object {

        private const val TYPE = "featureOverviewModal"

        fun fromWithNotificationType(notificationType: NotificationType): UpdatedModalComponent {
            val notificationTypeToUpdateFeatureContent =
                NotificationTypeToUpdateFeatureContent.fromWithNotificationType(notificationType)

            return UpdatedModalComponent(
                type = TYPE,
                textComponent = TextComponent.from(notificationTypeToUpdateFeatureContent.textComponentText),
                imageComponent = ImageComponent.fromSameSizeAsParentComponent(notificationTypeToUpdateFeatureContent.imageUrl),
                skipButtonComponent = ButtonComponent.from(notificationTypeToUpdateFeatureContent.skipButtonText),
                moveToUpdatedFeatureViewButtonComponent = ButtonComponent.ofWithDeepLink(
                    notificationTypeToUpdateFeatureContent.movedToUpdatedFeatureViewButtonText,
                    notificationTypeToUpdateFeatureContent.movedToUpdatedFeatureViewButtonUrl
                )
            )
        }

    }
}
