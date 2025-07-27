package com.wespot.view.color

data class Color(
    val value: String,
    val type: String = "Hex",
) {

    companion object {
        val DEFAULT_COLOR = Color(value = "#FFFFFF")
    }

}
