package com.wespot.view.image

import com.wespot.view.padding.Paddings

data class ImageContent(
    val url: String,
    val width: Int,
    val height: Int,
    val paddings: Paddings
) {
}
