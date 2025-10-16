package com.wespot.view.chip

import com.wespot.common.view.ColorType
import com.wespot.common.view.TypographType
import com.wespot.post.PostCategory
import com.wespot.view.color.Color
import com.wespot.view.icon.IconV2
import com.wespot.view.text.RichTextV2

data class FilterChip(
    val type: String = "FilterChip",
    val content: FilterChipContent,
) {

    data class FilterChipContent(
        val id: Long,
        val icon: IconV2 = IconV2(url = "", color = Color()),
        val text: RichTextV2,
        val target: String,
    ) {
    }

    companion object {

        fun of(
            id: Long,
            text: String,
        ): FilterChip {
            return FilterChip(
                content = FilterChipContent(
                    id = id,
                    icon = IconV2(url = "", color = Color()),
                    text = RichTextV2(
                        text = text,
                        color = Color(value = ColorType.GRAY600.value),
                        typography = TypographType.BODY06.value,
                        maxLine = 1
                    ),
                    target = text
                )
            )
        }

        fun of(
            id: Long,
            postCategory: PostCategory,
        ): FilterChip {
            return FilterChip(
                content = FilterChipContent(
                    id = id,
                    icon = IconV2(url = "", color = Color()),
                    text = RichTextV2(
                        text = postCategory.name,
                        color = Color(value = ColorType.GRAY600.value),
                        typography = TypographType.BODY06.value,
                        maxLine = 1,
                    ),
                    target = postCategory.name
                )
            )
        }
    }
}
