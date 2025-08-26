package com.wespot.post

import java.time.LocalDateTime

class PostCategory(
    val id: Long,
    val majorCategoryName: String,
    val name: String,
    val thumbnail: String,
    val backgroundImage: String,
    val createdAt: LocalDateTime
) {

    companion object {
        const val ALL_INCLUDE_CATEGORY_NAME = "전체"
    }

}
