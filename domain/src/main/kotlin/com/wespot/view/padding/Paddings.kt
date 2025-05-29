package com.wespot.view.padding

data class Paddings(
    val start: Int?,
    val end: Int?,
    val bottom: Int?,
    val top: Int?
) {

    companion object {
        fun of(start: Int? = null, end: Int? = null, bottom: Int? = null, top: Int? = null): Paddings {
            return Paddings(start, end, bottom, top)
        }

        val default = Paddings(null, null, null, null)
    }

}
