package com.wespot.user

enum class WithdrawalStatus {
    NONE,          // 탈퇴 x
    ACTIVE,        // 탈퇴 보류 중
    WITHDRAWN,     // 탈퇴 완료
    CANCELED       // 탈퇴 취소됨
}
