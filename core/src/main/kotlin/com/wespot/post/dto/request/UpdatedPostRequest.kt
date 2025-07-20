package com.wespot.post.dto.request

class UpdatedPostRequest(
    val categoryId: Long,
    val title: String?,
    val description: String,
    val imagesRequest: List<UpdatedPostImageRequest>?,
) {

    data class UpdatedPostImageRequest(
        val url: String,
        val width: Int,
        val height: Int
    ) {
    }

}
