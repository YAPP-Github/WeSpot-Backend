package com.wespot.user.port.`in`

import com.wespot.user.PolicyType
import com.wespot.user.dto.request.SearchUserRequest
import com.wespot.user.dto.request.UpdateProfileRequest
import com.wespot.user.dto.response.BackgroundListResponse
import com.wespot.user.dto.response.CharacterListResponse
import com.wespot.user.dto.response.UserListResponse
import com.wespot.user.dto.response.UserResponse


interface UserUseCase {

    fun me(): UserResponse

    fun updateProfile(profile: UpdateProfileRequest)

    fun backgrounds(): BackgroundListResponse

    fun characters(): CharacterListResponse

    fun allowNewPolicy(policyType: PolicyType): Long

}
