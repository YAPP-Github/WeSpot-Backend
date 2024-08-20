package com.wespot.auth

data class KakaoTemplate(
    val id: Long,
    val type : KakaoTemplateType,
    val title: String,
    val description: String,
    val imageUrl: String,
    val buttonText: String,
    val url: String
)
