package com.wespot.user

data class UserPolicyAgreement(
    val id: Long = 0L,
    val userId: Long,
    val policyType: PolicyType,
) {
}
