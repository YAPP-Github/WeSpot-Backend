package com.wespot.view.color

import com.wespot.common.view.ColorType

data class Color(
    val value: String = "GRAY600",
    val type: String = "Token",
) {

    companion object {

        const val TOKEN = "Token"
        const val HEX = "Hex"

        val DEFAULT_COLOR = Color(value = ColorType.BLACK.value)
    }

}
