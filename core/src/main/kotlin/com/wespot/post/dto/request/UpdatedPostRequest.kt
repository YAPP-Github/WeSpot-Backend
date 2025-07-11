package com.wespot.post.dto.request

class UpdatedPostRequest(
    val categoryId: Long,
    val title: String?,
    val description: String,
    val urlOfImages: List<String>,
) {
}
