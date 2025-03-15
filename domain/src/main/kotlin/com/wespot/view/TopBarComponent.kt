package com.wespot.view

import com.wespot.view.icon.Icon
import com.wespot.view.text.RichText

data class TopBarComponent(
    val type: String,
    val content: TopBarContent,
) {

    companion object {

        const val TYPE = "topBarComponent"

        fun of(richText: RichText, icons: List<Icon>): TopBarComponent {
            return TopBarComponent(
                TYPE,
                TopBarContent.of(richText, icons)
            )
        }

    }

}
