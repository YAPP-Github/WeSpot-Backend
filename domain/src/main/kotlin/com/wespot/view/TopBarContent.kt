package com.wespot.view

import com.wespot.view.icon.Icon
import com.wespot.view.icon.Icons
import com.wespot.view.text.RichText

data class TopBarContent(
    val richText: RichText,
    val icons: Icons
) {

    companion object {
        fun of(richText: RichText, icons: List<Icon>): TopBarContent {
            return TopBarContent(
                richText,
                Icons.from(icons)
            )
        }
    }

}
