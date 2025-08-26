package com.wespot.post.server_driven

import com.wespot.view.icon.IconV2
import com.wespot.view.text.RichTextV2

data class PostCategoryComponent(
    val text: RichTextV2,
    val target: String,
    val icon: IconV2,
) {
}
