package com.wespot.view.text

import com.wespot.view.color.StringColor
import com.wespot.view.padding.Paddings

data class ChipComponent(
    val type: String,
    val content: ChipContent
) {

    companion object {

        const val TYPE = "chipComponent"

        fun from(
            richText: RichText,
            paddings: Paddings,
            borderColor: String,
            containerColor: String
        ): ChipComponent {
            return ChipComponent(
                TYPE,
                ChipContent.of(
                    richText,
                    paddings,
                    StringColor.from(borderColor),
                    StringColor.from(containerColor)
                )
            )
        }

    }

}
