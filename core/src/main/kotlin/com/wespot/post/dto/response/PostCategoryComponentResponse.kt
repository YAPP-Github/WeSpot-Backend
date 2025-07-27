package com.wespot.post.dto.response

import com.wespot.common.dto.view.IconV2Response
import com.wespot.common.dto.view.RichTextV2Response
import com.wespot.post.server_driven.PostCategoryComponent

data class PostCategoryComponentResponse(
    val text: RichTextV2Response,
    val target: String,
    val icon: IconV2Response,
) {
    companion object {

        fun from(category: PostCategoryComponent?): PostCategoryComponentResponse? {
            return category?.let {
                PostCategoryComponentResponse(
                    text = RichTextV2Response.from(it.text),
                    target = it.target,
                    icon = IconV2Response.from(it.icon)
                )
            }
        }

    }

}
