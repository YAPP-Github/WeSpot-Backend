package com.wespot.view.color

data class Color(
    val value: String = "GRAY600",
    val type: String = "Token",
) {

    companion object {

        const val TOKEN = "Token"
        const val HEX = "Hex"

        val DEFAULT_COLOR = Color(value = "#FFFFFF")
    }

}
