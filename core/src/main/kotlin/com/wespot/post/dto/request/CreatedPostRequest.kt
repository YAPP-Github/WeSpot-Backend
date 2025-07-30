package com.wespot.post.dto.request

data class CreatedPostRequest(
    val categoryId: Long,
    val title: String?,
    val description: String,
    val imagesRequest: List<CreatedPostImageRequest>?,
) {

    data class CreatedPostImageRequest(
        val url: String
    ) {
    }

}
