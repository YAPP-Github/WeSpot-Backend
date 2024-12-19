package com.wespot.user

import com.wespot.EventUtils
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.image.Image
import com.wespot.image.event.CreatedImageWhenSignUpEvent
import org.springframework.http.HttpStatus

data class Profile(
    val id: Long,
    val backgroundColor: String,
    val iconUrl: String,
) {

    fun update(
        backgroundColor: String?,
        iconUrl: String?
    ) = Profile(
        id = this.id,
        backgroundColor = backgroundColor ?: this.backgroundColor,
        iconUrl = iconUrl ?: this.iconUrl
    )

    fun updateIconToImage(url: String): Profile {
        require(url.isNotBlank()) {
            throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "url은 필수로 존재해야합니다.")
        }
        return Profile(
            id = this.id,
            backgroundColor = "",
            iconUrl = url
        )
    }

    companion object {

        fun create(
            backgroundColor: String,
            iconUrl: String
        ) =
            Profile(
                id = 0,
                backgroundColor = backgroundColor,
                iconUrl = iconUrl
            )

        fun createInit(profileUrl: String?): Profile {
            return Profile(
                id = 0,
                backgroundColor = "",
                iconUrl = profileUrl ?: Image.INIT_PROFILE_ICON_URL
            )
        }

        fun createWithImage(image: Image): Profile {
            if (image.url == "") {
                return Profile(
                    id = 0,
                    backgroundColor = "",
                    iconUrl = Image.INIT_PROFILE_ICON_URL
                )
            }

            EventUtils.publish(CreatedImageWhenSignUpEvent(image))
            return Profile(
                id = 0,
                backgroundColor = "",
                iconUrl = image.url
            )
        }

    }
}
