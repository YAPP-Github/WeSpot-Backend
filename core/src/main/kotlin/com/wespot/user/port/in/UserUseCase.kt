package com.wespot.user.port.`in`

import com.wespot.user.Profile
import com.wespot.user.dto.request.UpdateProfileRequest
import com.wespot.user.dto.response.BackgroundListResponse
import com.wespot.user.dto.response.CharacterListResponse
import com.wespot.user.dto.response.UserResponse


interface UserUseCase {

    fun me(): UserResponse

    fun updateProfile(profile: UpdateProfileRequest): UserResponse

    fun backgrounds(): BackgroundListResponse

    fun characters(): CharacterListResponse

}
