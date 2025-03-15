package com.wespot.view.update

import com.wespot.common.NotificationTypeToUpdateFeatureContent
import com.wespot.view.button.link.DeepLink
import com.wespot.notification.NotificationType
import com.wespot.view.TopBarComponent
import com.wespot.view.button.ButtonsComponent
import com.wespot.view.image.ImageComponent
import com.wespot.view.text.ChipComponent
import com.wespot.view.text.TextComponent

data class UpdatedModalComponent(
    val type: String,
    val topBarComponent: TopBarComponent,
    val firstTextComponent: TextComponent,
    val secondTextComponent: TextComponent,
    val firstImageComponent: ImageComponent,
    val chipComponent: ChipComponent,
    val thirdTextComponent: TextComponent,
    val secondImageComponent: ImageComponent,
    val buttonsComponent: ButtonsComponent
) {

    companion object {

        private const val TYPE = "updateOverviewModal"

        fun fromWithNotificationType(notificationType: NotificationType): UpdatedModalComponent {
            val notificationTypeToUpdateFeatureContent =
                NotificationTypeToUpdateFeatureContent.fromWithNotificationType(notificationType)

            return UpdatedModalComponent(
                type = TYPE,
                topBarComponent = notificationTypeToUpdateFeatureContent.topBarComponent,
                firstTextComponent = notificationTypeToUpdateFeatureContent.textComponents[0],
                secondTextComponent = notificationTypeToUpdateFeatureContent.textComponents[1],
                firstImageComponent = notificationTypeToUpdateFeatureContent.imageComponents[0],
                chipComponent = notificationTypeToUpdateFeatureContent.chipComponent,
                thirdTextComponent = notificationTypeToUpdateFeatureContent.textComponents[2],
                secondImageComponent = notificationTypeToUpdateFeatureContent.imageComponents[1],
                buttonsComponent = notificationTypeToUpdateFeatureContent.buttonsComponent
            )
        }
    }

}
