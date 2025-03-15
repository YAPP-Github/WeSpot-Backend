package com.wespot.view.icon

data class Icons(
    val icon: List<Icon>
) {

    companion object {
        fun from(icons: List<Icon>): Icons {
            return Icons(icons)
        }
    }

}
