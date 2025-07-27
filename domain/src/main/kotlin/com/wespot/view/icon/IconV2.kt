package com.wespot.view.icon

import com.wespot.view.color.Color

data class IconV2(
    val url: String,
    val color: Color = Color.DEFAULT_COLOR,
) {
    companion object {

        val HOT_POST_ICON: IconV2 = IconV2(
            url = "https://cdn.wespot.app/icons/hot_post.svg",
            color = Color.DEFAULT_COLOR
        )

        val MOVE_TO_VOTE_ICON: IconV2 = IconV2(
            url = "https://cdn.wespot.app/icons/move_to_vote.svg",
            color = Color.DEFAULT_COLOR
        )

        val MOVE_TO_MESSAGE_ICON: IconV2 = IconV2(
            url = "https://cdn.wespot.app/icons/move_to_message.svg",
            color = Color.DEFAULT_COLOR
        )

    }
}
