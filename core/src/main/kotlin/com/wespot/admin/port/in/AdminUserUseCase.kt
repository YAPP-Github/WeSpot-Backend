package com.wespot.admin.port.`in`

import com.wespot.admin.dto.AdminUserResponse

interface AdminUserUseCase {

    fun getAllUsers(): List<AdminUserResponse>

}
