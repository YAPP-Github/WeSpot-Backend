package com.wespot.user.service

import com.wespot.auth.service.SecurityUtils
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.school.School
import com.wespot.school.SchoolType
import com.wespot.school.port.out.SchoolPort
import com.wespot.user.User
import com.wespot.user.dto.CursorSearchData
import com.wespot.user.dto.response.UserListResponse
import com.wespot.user.dto.response.UserResponse
import com.wespot.user.port.`in`.SearchUserUseCase
import com.wespot.user.port.out.BlockedUserPort
import com.wespot.user.port.out.UserPort
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class SearchUserService(
    private val userPort: UserPort,
    private val schoolPort: SchoolPort,
    private val blockedUserPort: BlockedUserPort
) : SearchUserUseCase {

    override fun searchUsers(
        keyword: String,
        cursorId: Long
    ): UserListResponse {
        val loginUser = SecurityUtils.getLoginUser(userPort)
        validateLoginUserRegulation(loginUser)
        val pageable = PageRequest.of(0, 10, Sort.by("id"))
        val blockedUserIds = blockedUserPort.findAllByBlockerId(blockerId = loginUser.id)
            .map { it.blockedId }

        if (cursorId == 0L) {
            return fetchFirstPage(
                keyword = keyword,
                pageable = pageable,
                loginUserId = loginUser.id,
                blockedUserIds = blockedUserIds
            )
        }

        val cursorData = fetchCursorData(cursorId)
        val users = userPort.searchUsers(
            name = keyword,
            cursorName = cursorData.cursorName,
            cursorSchoolName = cursorData.cursorSchoolName,
            cursorSchoolTypeOrder = cursorData.cursorSchoolTypeOrder,
            cursorId = cursorId,
            pageable = pageable,
            loginUserId = loginUser.id,
            blockedUserIds = blockedUserIds
        )

        val totalCount = userPort.countUsersAfterCursor(
            name = keyword,
            cursorName = cursorData.cursorName,
            cursorSchoolName = cursorData.cursorSchoolName,
            cursorSchoolTypeOrder = cursorData.cursorSchoolTypeOrder,
            cursorId = cursorId,
            loginUserId = loginUser.id,
            blockedUserIds = blockedUserIds
        )

        return buildUserListResponse(users = users, pageable = pageable, totalCount = totalCount)
    }

    private fun validateLoginUserRegulation(loginUser: User) {
        if (loginUser.isRegulation()) {
            throw CustomException(HttpStatus.FORBIDDEN, ExceptionView.TOAST, "규제를 당한 유저는 해당 서비스를 사용할 수 없습니다.")
        }
    }

    private fun fetchFirstPage(
        keyword: String,
        pageable: Pageable,
        loginUserId: Long,
        blockedUserIds: List<Long>,
    ): UserListResponse {
        val users = userPort.searchUsers(
            name = keyword,
            cursorName = null,
            cursorSchoolName = null,
            cursorSchoolTypeOrder = null,
            cursorId = null,
            pageable = pageable,
            loginUserId = loginUserId,
            blockedUserIds = blockedUserIds,
        )

        val totalCount = userPort.countUsersAfterCursor(
            name = keyword,
            cursorName = null,
            cursorSchoolName = null,
            cursorSchoolTypeOrder = null,
            cursorId = null,
            loginUserId = loginUserId,
            blockedUserIds = blockedUserIds,
        )

        return buildUserListResponse(users = users, pageable = pageable, totalCount = totalCount)
    }

    private fun fetchCursorData(cursorId: Long): CursorSearchData {
        val cursorUser = userPort.findById(cursorId)
            ?: throw CustomException(HttpStatus.NOT_FOUND, ExceptionView.TOAST, "사용자 정보를 찾을 수 없습니다.")
        val school = cursorUser.school

        val cursorSchoolTypeOrder = when (school.schoolType) {
            SchoolType.MIDDLE -> 1
            SchoolType.HIGH -> 2
        }

        return CursorSearchData(
            cursorName = cursorUser.name,
            cursorSchoolName = school.name,
            cursorSchoolTypeOrder = cursorSchoolTypeOrder
        )
    }

    private fun buildUserListResponse(
        users: List<User>,
        pageable: Pageable,
        totalCount: Long
    ): UserListResponse {
        val hasNext = totalCount > pageable.pageSize
        val userResponses = users.map { user ->
            val school = user.school
            UserResponse.from(user, school.name)
        }
        return UserListResponse.from(users = userResponses, hasNext = hasNext)
    }

}
