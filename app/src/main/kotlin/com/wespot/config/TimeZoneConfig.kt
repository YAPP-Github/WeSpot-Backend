package com.wespot.config

import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.util.*

@Configuration
@Profile("!local")
class TimeZoneConfig {

    @PostConstruct
    fun timeZoneSetUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
    }

}
