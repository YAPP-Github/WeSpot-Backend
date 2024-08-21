package com.wespot.auth

import jakarta.persistence.*

@Entity
@Table(name = "kakao_template")
class KakaoTemplateJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val title: String,

    val description: String,

    val imageUrl: String,

    val buttonText: String,

    val url: String,

    @Enumerated(EnumType.STRING)
    val type: KakaoTemplateType,

) {
}
