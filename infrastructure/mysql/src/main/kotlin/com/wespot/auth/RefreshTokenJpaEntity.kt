package com.wespot.auth

import com.wespot.user.entity.UserJpaEntity
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import java.time.LocalDateTime


@Entity
@Table(name = "refresh_token")
class RefreshTokenJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val refreshToken: String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = ForeignKey(name = "fk_refresh_token_user_id"))
    val user: UserJpaEntity,

    @CreatedDate
    @LastModifiedDate
    val createdAt: Instant? = null,

    @LastModifiedDate
    val updatedAt: Instant? = null,

    @Column(name = "expired_at", columnDefinition = "Datetime", scale = 6)
    val expiredAt: LocalDateTime? = null,
) {

}


