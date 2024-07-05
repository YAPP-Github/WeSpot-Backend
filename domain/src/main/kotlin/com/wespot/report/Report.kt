package com.wespot.report

import com.wespot.user.User

data class Report(
    val type: ReportType,
    val reporter: User,
    val reported: User,
    val isApprove: Boolean
) {

}
