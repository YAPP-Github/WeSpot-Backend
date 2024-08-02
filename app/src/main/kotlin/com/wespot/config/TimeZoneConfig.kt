package com.wespot.config

import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class TimeZoneConfig {

    @PostConstruct
    fun init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
    }

}
