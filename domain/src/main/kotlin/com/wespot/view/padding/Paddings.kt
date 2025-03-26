package com.wespot.view.padding

data class Paddings(
    val start: Int?,
    val end: Int?,
    val bottom: Int?
) {

    companion object {
        fun of(start: Int?, end: Int?, bottom: Int?, top: Int?): Paddings {
            return Paddings(start, end, bottom)
        }
    }

}
