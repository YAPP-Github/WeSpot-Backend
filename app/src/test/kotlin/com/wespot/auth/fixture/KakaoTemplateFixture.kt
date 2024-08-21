package com.wespot.auth.fixture

import com.wespot.auth.KakaoTemplate
import com.wespot.auth.KakaoTemplateType

object KakaoTemplateFixture {

    fun createKakaoTemplate(): KakaoTemplate {
        return KakaoTemplate(
            id = 1L,
            type = KakaoTemplateType.TELL,
            title = "title",
            description = "description",
            imageUrl = "imageUrl",
            buttonText = "buttonText",
            url = "url"
        )
    }
}
