package com.wespot.common.dto.view

import com.wespot.post.nudge.server_driven.MessageComponent

data class MessageComponentResponse(
    val type: String,
    val id: Long,
    val content: MessageContentComponentResponse,
) {

    data class MessageContentComponentResponse(
        val thumbnail: ImageContentV2Response,
        val title: RichTextV2Response,
        val description: RichTextV2Response,
        val icon: IconV2Response,
    )

    companion object {
        fun from(
            messageComponent: MessageComponent
        ): MessageComponentResponse {
            return MessageComponentResponse(
                id = messageComponent.id,
                type = messageComponent.type,
                content = MessageContentComponentResponse(
                    thumbnail = ImageContentV2Response.from(messageComponent.content.thumbnail),
                    title = RichTextV2Response.from(messageComponent.content.title),
                    description = RichTextV2Response.from(messageComponent.content.description),
                    icon = IconV2Response.from(messageComponent.content.icon)
                )
            )
        }
    }

}
