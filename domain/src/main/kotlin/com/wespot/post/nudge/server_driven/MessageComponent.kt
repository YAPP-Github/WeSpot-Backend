package com.wespot.post.nudge.server_driven

import com.wespot.common.view.ColorType
import com.wespot.common.view.TypographType
import com.wespot.view.color.Color
import com.wespot.view.icon.IconV2
import com.wespot.view.image.ImageContentV2
import com.wespot.view.text.RichTextV2

data class MessageComponent(
    val type: String = "BannerItem",
    val id: Long,
    val content: MessageContentComponent,
) {

    data class MessageContentComponent(
        val thumbnail: ImageContentV2,
        val title: RichTextV2,
        val description: RichTextV2,
        val icon: IconV2,
    )

    companion object {
        fun from(
            id: Long,
        ): MessageComponent {
            return MessageComponent(
                id = id,
                content = MessageContentComponent(
                    thumbnail = ImageContentV2(
                        url = "https://dw2d2daekmyur.cloudfront.net/message_nudge.png",
                        width = 40,
                        height = 40
                    ),
                    title = RichTextV2(
                        text = "답장을 기다리는 쪽지가 있어요",
                        color = Color(value = ColorType.GRAY100.value),
                        typography = TypographType.BODY03.value,
                        maxLine = 1
                    ),
                    description = RichTextV2(
                        text = "눌러서 바로 확인하기",
                        color = Color(value = ColorType.PRIMARY300.value),
                        typography = TypographType.BODY07.value,
                        maxLine = 1
                    ),
                    icon = IconV2.MOVE_TO_MESSAGE_ICON
                )
            )
        }
    }

}
