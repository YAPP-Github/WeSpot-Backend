package com.wespot.common.dto

data class PostPagingResponse(
    val data: List<Any>,
    val lastCursorId: Long? = null,
    val hasNext: Boolean
) {

}
