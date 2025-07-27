package com.wespot.post.nudge.server_driven

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
                        url = "https://example.com/image.jpg",
                        width = 100,
                        height = 100
                    ),
                    title = RichTextV2(text = "답장을 기다리는 쪽지가 있어요"),
                    description = RichTextV2(text = "눌러서 바로 확인하기"),
                    icon = IconV2.MOVE_TO_MESSAGE_ICON
                )
            )
        }
    }

}
