package com.wespot

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {

    @GetMapping("/health")
    fun health(): String {
        return "Wespot Server is running"
    }

    @GetMapping("/")
    fun healthNone(): String {
        return "Wespot Server is running"
    }

    @PostMapping("/")
    fun healthPost(): String {
        return "Wespot Server is running"
    }

}
