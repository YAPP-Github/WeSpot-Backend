package com.wespot.firebase

import com.wespot.common.service.ServiceTest
import com.wespot.notification.LatestVersionType
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.Test

class RemoteConfigTest @Autowired constructor(
    private val latestVersionGetter: LatestVersionGetter
) : ServiceTest() {

    @Test
    fun `RemoteConfig 값을 가져올 수 있다`() {
        // given
        val android = LatestVersionType.ANDROID
        val ios = LatestVersionType.IOS

        // when
        val androidVersion = latestVersionGetter.get(android)
        val iosVersion = latestVersionGetter.get(ios)

        // then
        androidVersion shouldNotBe null
        iosVersion shouldNotBe null
    }

}
