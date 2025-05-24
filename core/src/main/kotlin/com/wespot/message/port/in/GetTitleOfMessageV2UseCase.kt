package com.wespot.message.port.`in`

import com.wespot.message.dto.response.TitleOfMessageV2Response

interface GetTitleOfMessageV2UseCase {

    fun getTitle(): TitleOfMessageV2Response

}
