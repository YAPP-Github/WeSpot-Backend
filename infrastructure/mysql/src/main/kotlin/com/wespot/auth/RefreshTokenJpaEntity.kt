package com.wespot.auth

import com.wespot.common.BaseEntity
import com.wespot.user.entity.UserJpaEntity
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "refresh_token")
class RefreshTokenJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var refreshToken: String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", foreignKey = ForeignKey(name = "fk_refresh_token_user_id"))
    val user: UserJpaEntity,

    @Embedded
    val baseEntity: BaseEntity,

    @field:Column(name = "expired_at", columnDefinition = "Datetime", scale = 6)
    var expiredAt: LocalDateTime,
) {

    fun update(refreshToken: String) {
        this.refreshToken = refreshToken
        this.expiredAt= LocalDateTime.now().plusDays(30)
    }
}


