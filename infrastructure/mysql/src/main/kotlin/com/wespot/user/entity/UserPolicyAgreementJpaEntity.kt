package com.wespot.user.entity

import com.wespot.user.PolicyType
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "user_policy_agreement")
class UserPolicyAgreementJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @field: NotNull
    val userId: Long,

    @Enumerated(EnumType.STRING)
    @field: NotNull
    val policyType: PolicyType,
) {
}
