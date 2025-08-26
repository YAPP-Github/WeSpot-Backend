package com.wespot.common.dto

import com.wespot.common.dto.view.ImageContentV2Response

data class PostPagingResponse(
    val data: List<Any>,

    val background: ImageContentV2Response? = null,
    val thumbnail: ImageContentV2Response? = null,

    val lastCursorId: Long? = null,
    val hasNext: Boolean
) {

}
