package com.wespot.user.message

import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.image.Image
import com.wespot.user.User
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class AnonymousProfile(
    val id: Long,
    val imageUrl: String,
    val name: String,
    val owner: User,
    val receiverId: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {

        fun createInitial(
            image: Image,
            profileName: ProfileName,
            owner: User,
            receiverId: Long,
        ): AnonymousProfile {
            return AnonymousProfile(
                id = 0,
                imageUrl = image.url,
                name = profileName.name,
                owner = owner,
                receiverId = receiverId,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        }

        fun of(
            id: Long,
            imageUrl: String,
            name: String,
            owner: User,
            receiverId: Long,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime
        ): AnonymousProfile {
            return AnonymousProfile(
                id = id,
                imageUrl = imageUrl,
                name = name,
                owner = owner,
                receiverId = receiverId,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }

    fun update(image: Image, profileName: ProfileName, accessUserId: Long): AnonymousProfile {
        if (accessUserId != owner.id) {
            throw CustomException(
                HttpStatus.FORBIDDEN,
                ExceptionView.TOAST,
                "해당 익명 프로필의 소유주가 아니어 변경할 수 없습니다."
            )
        }
        return AnonymousProfile(
            id = id,
            imageUrl = image.url,
            name = profileName.name,
            owner = owner,
            receiverId = receiverId,
            createdAt = createdAt,
            updatedAt = LocalDateTime.now()
        )
    }

}
