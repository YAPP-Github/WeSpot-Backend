package com.wespot.auth.service

import feign.Logger
import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType

class SocialHeaderConfiguration {
    companion object {
        private const val CONTENT_TYPE_HEADER = "Content-Type"
    }

    @Bean
    fun requestInterceptor(): RequestInterceptor {
        return RequestInterceptor { template: RequestTemplate ->
            template.header(
                CONTENT_TYPE_HEADER,
                MediaType.APPLICATION_FORM_URLENCODED_VALUE
            )
        }
    }

    @Bean
    fun feignLoggerLevel(): Logger.Level {
        return Logger.Level.FULL
    }
}
