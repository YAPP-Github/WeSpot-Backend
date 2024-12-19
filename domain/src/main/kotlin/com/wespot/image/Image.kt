package com.wespot.image

import com.wespot.EventUtils
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.image.event.UpdateProfileImageEvent
import com.wespot.user.Profile
import jdk.jfr.Event
import org.springframework.http.HttpStatus
import java.time.LocalDateTime
import java.util.*

data class Image(
    val id: Long,
    val url: String,
    val createdAt: LocalDateTime,
) {

    companion object {

        const val INIT_PROFILE_ICON_URL =
            "https://velog.velcdn.com/images/kpeel5839/post/56a802d1-2b99-46a7-9597-f75f788f61ed/image.png"
        private val BASIC_IMAGE: Image = Image(0L, INIT_PROFILE_ICON_URL, LocalDateTime.MIN)

        fun ofWithSignUp(url: String?, cloudFrontUrl: String): Image {
            if (url == null) {
                return Image(0L, "", LocalDateTime.now())
            }

            return createImage(url, cloudFrontUrl)
        }

        fun ofWithUpdateProfile(url: String?, cloudFrontUrl: String, savedImage: (Image) -> Image): Image {
            if (Objects.isNull(url)) {
                EventUtils.publish(UpdateProfileImageEvent(BASIC_IMAGE))
                return BASIC_IMAGE
            }
            val image = createImage(url!!, cloudFrontUrl)
            EventUtils.publish(UpdateProfileImageEvent(image))
            return savedImage(image)
        }

        private fun createImage(url: String, cloudFrontUrl: String): Image {
            require(url.isNotBlank()) {
                throw CustomException(
                    HttpStatus.BAD_REQUEST,
                    ExceptionView.TOAST,
                    "url은 필수로 존재해야합니다."
                )
            }

            require(cloudFrontUrl.isNotBlank()) {
                throw CustomException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ExceptionView.TOAST,
                    "clientUrl은 필수로 존재해야합니다."
                )
            }

            return Image(0L, "$cloudFrontUrl/$url", LocalDateTime.now())
        }

    }

}
