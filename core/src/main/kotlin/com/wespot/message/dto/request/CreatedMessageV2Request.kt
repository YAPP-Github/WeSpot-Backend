package com.wespot.message.dto.request

class CreatedMessageV2Request(
    val content: String,
    val receiverId: Long,

    val isAnonymous: Boolean,
    val anonymousImageUrl: String,
    val anonymousProfileName: String,
) {

}
