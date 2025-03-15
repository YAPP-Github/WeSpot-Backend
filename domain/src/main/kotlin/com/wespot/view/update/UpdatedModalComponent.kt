package com.wespot.view.update

import com.wespot.common.NotificationTypeToUpdateFeatureContent
import com.wespot.view.button.link.DeepLink
import com.wespot.common.view.*
import com.wespot.notification.NotificationType

data class UpdatedModalComponent(
    val type: String,
    val topBarComponent: TopBarComponent,
    val titleComponent: TitleComponent,
    val subTitleComponent: SubTitleComponent,
    val imageComponent: ImageComponent,
    val chipComponent: ChipComponent,
    val descriptionComponent: DescriptionComponent,
    val descriptionImageComponent: DescriptionImageComponent,
    val buttonListComponent: ButtonListComponent
) {

    companion object {

        private const val TYPE = "updateOverviewModal"

        fun fromWithNotificationType(notificationType: NotificationType): com.wespot.view.update.UpdatedModalComponent {
            val notificationTypeToUpdateFeatureContent =
                NotificationTypeToUpdateFeatureContent.fromWithNotificationType(notificationType)

            return com.wespot.view.update.UpdatedModalComponent(
                type = com.wespot.view.update.UpdatedModalComponent.Companion.TYPE,
                topBarComponent = TopBarComponent.from(notificationTypeToUpdateFeatureContent.topBarComponentText),
                titleComponent = TitleComponent.from(notificationTypeToUpdateFeatureContent.titleComponentText),
                subTitleComponent = SubTitleComponent.from(notificationTypeToUpdateFeatureContent.subTitleComponentText),
                imageComponent = ImageComponent.of(
                    notificationTypeToUpdateFeatureContent.imageComponentURL,
                    notificationTypeToUpdateFeatureContent.imageComponentWidth,
                    notificationTypeToUpdateFeatureContent.imageComponentHeight
                ),
                chipComponent = ChipComponent.from(notificationTypeToUpdateFeatureContent.chipComponentText),
                descriptionComponent = DescriptionComponent.from(
                    notificationTypeToUpdateFeatureContent.descriptionComponentText
                ),
                descriptionImageComponent = DescriptionImageComponent.of(
                    notificationTypeToUpdateFeatureContent.descriptionImageComponentURL,
                    notificationTypeToUpdateFeatureContent.descriptionImageComponentWidth,
                    notificationTypeToUpdateFeatureContent.descriptionImageComponentHeight
                ),
                ButtonListComponent.from(
                    notificationTypeToUpdateFeatureContent.buttonComponentProperties
                        .map {
                            InnerButtonComponent.of(
                                it[0] as String,
                                it[1] as String,
                                it[2] as String,
                                it[3] as String,
                                it[4] as OnClickActionType,
                                it[5] as DeepLink
                            )
                        }
                        .toList()
                )
            )
        }

    }
}
