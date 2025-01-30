package com.wespot.user.service.listener

import com.wespot.auth.service.SecurityUtils
import com.wespot.image.event.UpdateProfileImageEvent
import com.wespot.image.out.ImagePort
import com.wespot.image.out.S3Port
import com.wespot.user.port.out.ProfilePort
import com.wespot.user.port.out.UserPort
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UserEventListener(
    private val userPort: UserPort,
    private val profilePort: ProfilePort,
    private val s3Port: S3Port,
    private val imagePort: ImagePort
) {

    @EventListener
    fun signUpNewUser(event: UpdateProfileImageEvent) {
        val loginUser = SecurityUtils.getLoginUser(userPort)
        val profile = loginUser.profile

        imagePort.deleteByUrl(profile.iconUrl)
        s3Port.delete(profile.iconUrl)
        profilePort.save(profile.updateIconToImage(event.image.url))

        loginUser.updateIntroduction(event.introduction)

        userPort.save(loginUser)
    }

}
