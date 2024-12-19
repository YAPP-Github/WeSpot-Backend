package com.wespot.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(apiInfo())
            .components(Components())
    }

    private fun apiInfo(): Info {
        return Info()
            .title("wespot")
            .description("wespot Swagger UI")
            .version("1.0.0")
    }

}
