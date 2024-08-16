package com.wespot.config

import com.wespot.error.CustomErrorDecoder
import feign.codec.ErrorDecoder
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate


@Configuration
@EnableFeignClients(basePackages = ["com.wespot"])
class OpenFeignConfig {

    @Bean
    fun errorDecoder(): ErrorDecoder {
        return CustomErrorDecoder()
    }

    @Bean
    fun feignMessageConverter(): MappingJackson2HttpMessageConverter {
        return MappingJackson2HttpMessageConverter()
    }

    @Bean
    fun restTemplate(messageConverters: List<HttpMessageConverter<*>>): RestTemplate {
        return RestTemplateBuilder().additionalMessageConverters(messageConverters).build()
    }
}
