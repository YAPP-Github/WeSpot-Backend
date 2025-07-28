package com.wespot.view.icon

import com.wespot.common.view.ColorType
import com.wespot.view.color.Color

data class IconV2(
    val url: String,
    val color: Color = Color.DEFAULT_COLOR,
) {
    companion object {

        val HOT_POST_ICON: IconV2 = IconV2(
            url = "https://dw2d2daekmyur.cloudfront.net/hot_post.png",
            color = Color.DEFAULT_COLOR
        )

        val MOVE_TO_VOTE_ICON: IconV2 = IconV2(
            url = "https://dw2d2daekmyur.cloudfront.net/move_to_message.png",
            color = Color(value = ColorType.WHITE.value)
        )

        val MOVE_TO_MESSAGE_ICON: IconV2 = IconV2(
            url = "https://dw2d2daekmyur.cloudfront.net/move_to_message.png",
            color = Color(value = ColorType.WHITE.value)
        )

    }
}
