package com.wespot.user.domain

import com.wespot.user.UserVersion
import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.LocalDateTime

class UserVersionTest {

    @ParameterizedTest
    @CsvSource(
        value = [
            "0.0.0,0.0.0,0.0.0,0.0.0,false",
            "0.0.0,0.0.0,1.3.0,0.0.0,true",
            "0.0.0,0.0.0,1.3.0,1.2.0,true",
            "1.3.0,0.0.0,0.0.0,0.0.0,true",
            "1.3.0,1.2.0,0.0.0,0.0.0,true",
            "1.3.0,1.2.0,1.3.0,1.3.0,false",
            "1.3.0,1.3.0,1.3.0,1.2.0,false",
            "1.3.0,1.3.0,0.0.0,0.0.0,false",
            "0.0.0,0.0.0,1.3.0,1.3.0,false",
            "1.3.0,1.3.0,1.3.0,1.3.0,false",
            "1.3.0,1.2.1,1.3.0,0.0.0,true",
        ], delimiter = ','
    )
    fun `회원가입 후 버전 업데이트를 한 유저의 Version인지 확인한다`(
        iosVersionName: String,
        iosVersionNameWhenSignUp: String,
        androidVersionName: String,
        androidVersionNameWhenSignUp: String,
        expected: Boolean
    ) {
        // given
        val userVersion = UserVersion(
            id = 0L,
            userId = 0L,
            iosVersionName = iosVersionName,
            iosVersionNameWhenSignUp = iosVersionNameWhenSignUp,
            androidVersionName = androidVersionName,
            androidVersionNameWhenSignUp = androidVersionNameWhenSignUp,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        // when
        val actual = userVersion.hasLatestVersion("1.3.0", "1.3.0")

        // then
        actual shouldBe expected
    }

}
