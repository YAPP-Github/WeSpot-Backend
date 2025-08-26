package com.wespot.report

import com.wespot.report.dto.ReportReasonResponse
import com.wespot.report.port.`in`.ReportReasonUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/reports")
class ReportReasonController(
    private val reportReasonService: ReportReasonUseCase
) {

    @GetMapping
    fun getReportReasons(): ResponseEntity<List<ReportReasonResponse>> {
        val response = reportReasonService.getReportReasons()

        return ResponseEntity.ok(response)
    }

}
