package com.wespot.school

import com.wespot.school.dto.SchoolListResponse
import com.wespot.school.port.`in`.SchoolUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/schools")
class SchoolController(
    private val schoolUseCase: SchoolUseCase
) {

    @GetMapping("/search")
    fun searchSchools(
        @RequestParam name: String,
        @RequestParam(required = false) cursorId: Long?,
    ): ResponseEntity<SchoolListResponse> {
        return ResponseEntity.ok(
            schoolUseCase.searchSchools(
                keyword = name,
                cursorId = cursorId ?: 0
            )
        )
    }
}
