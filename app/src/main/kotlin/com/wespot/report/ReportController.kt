package com.wespot.report

import com.wespot.report.dto.ReportRequest
import com.wespot.report.dto.ReportResponse
import com.wespot.report.port.`in`.SavedReportUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/votes")
class ReportController(
    val savedReportUseCase: SavedReportUseCase
) {

    @PostMapping("/users/{userId}/reports")
    fun createReport(
        @PathVariable("userId") targetUserId: Long,
        @RequestBody reportRequest: ReportRequest,
    ): ResponseEntity<ReportResponse> {
        val response: ReportResponse = savedReportUseCase.reportReceived(reportRequest)

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(response)
    }

}
