package com.wespot.view.text

enum class Align(val value: String) {

    CENTER("Center"),
    START("Start"),
    END("End")
    ;

    companion object {
        fun from(align: String): Align {
            return entries
                .first { it.value == align }
        }
    }

}
