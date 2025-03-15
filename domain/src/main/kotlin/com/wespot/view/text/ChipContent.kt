package com.wespot.view.text

import com.wespot.view.color.StringColor
import com.wespot.view.padding.Paddings

data class ChipContent(
    val richText: RichText,
    val paddings: Paddings,
    val borderColor: StringColor,
    val containerColor: StringColor
) {

    companion object {

        fun of(
            richText: RichText,
            paddings: Paddings,
            borderColor: StringColor,
            containerColor: StringColor
        ): ChipContent {
            return ChipContent(
                richText,
                paddings,
                borderColor,
                containerColor
            )
        }

    }

}
