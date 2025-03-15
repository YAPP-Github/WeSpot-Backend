package com.wespot.view.icon

import com.wespot.view.button.OnClickAction

data class Icon(
    val url: String,
    val width: Int,
    val height: Int,
    val onClickAction: OnClickAction
) {

}
