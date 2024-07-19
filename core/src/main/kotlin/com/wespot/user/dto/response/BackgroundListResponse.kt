package com.wespot.user.dto.response

data class BackgroundListResponse(
    val backgrounds: List<BackgroundResponse>
) {
    companion object {

        fun from(backgrounds: List<BackgroundResponse>): BackgroundListResponse {
            return BackgroundListResponse(
                backgrounds = backgrounds
            )
        }

    }
}
