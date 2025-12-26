package com.wespot.view.icon

import com.wespot.common.view.ColorType
import com.wespot.view.color.Color

data class IconV2(
    val url: String,
    val color: Color = Color.DEFAULT_COLOR,
) {
    companion object {

        val RIGHT_ARROW_IN_BLACK = IconV2(
            url = "https://dw2d2daekmyur.cloudfront.net/icon-arrow-back-fill.png",
            color = Color(value = ColorType.GRAY300.value)
        )

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

        val COMMENT_BALLON = IconV2(
            url = "https://dw2d2daekmyur.cloudfront.net/comment_ballon.png",
        )

        val THUMBS_UP = IconV2(
            url = "https://dw2d2daekmyur.cloudfront.net/thumbs_up.png",
        )

        val COMMENT_NOTIFICATION_CHECK = IconV2(
            url = "https://dw2d2daekmyur.cloudfront.net/comment_notification_check.png",
        )

        val BOOKMARK = IconV2(
            url = "https://dw2d2daekmyur.cloudfront.net/bookmark.png",
        )

        val RIGHT_ARROW = IconV2(
            url = "https://dw2d2daekmyur.cloudfront.net/icon-arrow-back.jpg",
            color = Color(value = ColorType.GRAY600.value)
        )

    }
}
