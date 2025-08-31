package com.wespot.report

data class ReportReasonWithCustomReason(
    val reportReason: ReportReason,
    val customReason: String?,
) {

    init {
        if (!reportReason.canReceiveReason && customReason != null) {
            throw IllegalArgumentException("신고 사유를 입력 받을 수 없는 유형입니다.")
        }
    }

}
