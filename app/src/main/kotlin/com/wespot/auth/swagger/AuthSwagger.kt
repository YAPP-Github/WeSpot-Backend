package com.wespot.auth.swagger

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "Auth API", description = "Auth API")
interface AuthSwagger {

    @Operation(
        summary = "Logout API 입니다.",
        description = "현재로써는 FCM만 날리고 있습니다"
    )
    fun logout(): ResponseEntity<Unit>

}
