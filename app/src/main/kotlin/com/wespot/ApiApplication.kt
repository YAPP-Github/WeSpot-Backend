package com.wespot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication(scanBasePackages = ["com.wespot"])
@EnableAsync
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
