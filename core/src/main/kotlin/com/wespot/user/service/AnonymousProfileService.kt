package com.wespot.user.service

import com.wespot.EventUtils
import com.wespot.auth.service.SecurityUtils
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.image.Image
import com.wespot.image.event.DeletedImageEvent
import com.wespot.image.event.SavedImageEvent
import com.wespot.user.dto.request.CreatedAnonymousProfileRequest
import com.wespot.user.dto.request.UpdatedAnonymousProfileRequest
import com.wespot.user.message.AnonymousProfile
import com.wespot.user.message.ProfileName
import com.wespot.user.port.`in`.AnonymousProfileUseCase
import com.wespot.user.port.out.AnonymousProfilePort
import com.wespot.user.port.out.UserPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AnonymousProfileService(
    private val anonymousProfilePort: AnonymousProfilePort,
    private val userPort: UserPort,
    @Value("\${aws.cloud-front.url}")
    private val cloudFrontUrl: String,
) : AnonymousProfileUseCase {

    @Transactional
    override fun createAnonymousProfile(createdAnonymousProfileRequest: CreatedAnonymousProfileRequest): AnonymousProfile {
        val loginUser = SecurityUtils.getLoginUser(userPort)
        val receiver = userPort.findById(createdAnonymousProfileRequest.receiverId) ?: throw CustomException(
            HttpStatus.NOT_FOUND,
            ExceptionView.TOAST,
            "상대방을 찾을 수 없어 익명 프로필을 생성할 수 없습니다."
        )
        val users = userPort.findAll()
        val image = Image.createImage(createdAnonymousProfileRequest.imageUrl, cloudFrontUrl)
        val alreadyExistsAnonymousProfileWithReceiver = anonymousProfilePort.findAllByOwnerIdAndReceiverId(
            loginUser.id,
            receiver.id
        )
        val profileName = ProfileName.of(
            createdAnonymousProfileRequest.name,
            users,
            alreadyExistsAnonymousProfileWithReceiver
        )

        val anonymousProfile = AnonymousProfile.createInitial(
            image = image,
            profileName = profileName,
            owner = loginUser,
            receiver = receiver,
            alreadyExistsAnonymousProfileWithReceiver = alreadyExistsAnonymousProfileWithReceiver,
        )

        val savedAnonymousProfile = anonymousProfilePort.save(anonymousProfile)
        EventUtils.publish(SavedImageEvent(image))
        return savedAnonymousProfile
    }

    @Transactional
    override fun updateAnonymousProfile(
        profileId: Long,
        updatedAnonymousProfileRequest: UpdatedAnonymousProfileRequest
    ): AnonymousProfile {
        val loginUser = SecurityUtils.getLoginUser(userPort)
        val savedAnonymousProfile = anonymousProfilePort.findByProfileId(profileId) ?: throw CustomException(
            HttpStatus.BAD_REQUEST,
            ExceptionView.TOAST,
            "실명 프로필로를 찾을 수 없습니다."
        )
        val users = userPort.findAll()
        val alreadyExistsAnonymousProfileWithReceiver = anonymousProfilePort.findAllByOwnerIdAndReceiverId(
            loginUser.id,
            savedAnonymousProfile.receiver.id
        )
        val image = Image.createImage(updatedAnonymousProfileRequest.imageUrl, cloudFrontUrl)
        val profileName =
            ProfileName.of(updatedAnonymousProfileRequest.name, users, alreadyExistsAnonymousProfileWithReceiver)


        val updatedAnonymousProfile = savedAnonymousProfile.update(
            image, profileName, loginUser.id
        )
        val savedAnonymousProfileAfterUpdate = anonymousProfilePort.save(updatedAnonymousProfile)

        EventUtils.publish(SavedImageEvent(image))
        EventUtils.publish(DeletedImageEvent(savedAnonymousProfile.imageUrl))
        return savedAnonymousProfileAfterUpdate
    }

}
