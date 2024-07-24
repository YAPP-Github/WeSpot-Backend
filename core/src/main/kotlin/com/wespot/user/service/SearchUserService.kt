package com.wespot.user.service

import com.wespot.school.School
import com.wespot.school.SchoolType
import com.wespot.school.port.out.SchoolPort
import com.wespot.user.User
import com.wespot.user.dto.CursorSearchData
import com.wespot.user.dto.response.UserListResponse
import com.wespot.user.dto.response.UserResponse
import com.wespot.user.port.`in`.SearchUserUseCase
import com.wespot.user.port.out.UserPort
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class SearchUserService(
    private val userPort: UserPort,
    private val schoolPort: SchoolPort
) : SearchUserUseCase {

    override fun searchUsers(
        keyword: String,
        cursorId: Long
    ): UserListResponse {
        val pageable = PageRequest.of(0, 10, Sort.by("id"))

        if (cursorId == 0L) {
            return fetchFirstPage(keyword = keyword, pageable = pageable)
        }

        val cursorData = fetchCursorData(cursorId)
        val users = userPort.searchUsers(
            name = keyword,
            cursorName = cursorData.cursorName,
            cursorSchoolName = cursorData.cursorSchoolName,
            cursorSchoolTypeOrder = cursorData.cursorSchoolTypeOrder,
            cursorId = cursorId,
            pageable = pageable
        )

        val totalCount = userPort.countUsersAfterCursor(
            name = keyword,
            cursorName = cursorData.cursorName,
            cursorSchoolName = cursorData.cursorSchoolName,
            cursorSchoolTypeOrder = cursorData.cursorSchoolTypeOrder,
            cursorId = cursorId
        )

        return buildUserListResponse(users = users, pageable = pageable, totalCount = totalCount)
    }

    private fun fetchFirstPage(
        keyword: String,
        pageable: Pageable
    ): UserListResponse {
        val users = userPort.searchUsers(
            name = keyword,
            cursorName = null,
            cursorSchoolName = null,
            cursorSchoolTypeOrder = null,
            cursorId = null,
            pageable = pageable
        )

        val totalCount = userPort.countUsersAfterCursor(
            name = keyword,
            cursorName = null,
            cursorSchoolName = null,
            cursorSchoolTypeOrder = null,
            cursorId = null
        )

        return buildUserListResponse(users = users, pageable = pageable, totalCount = totalCount)
    }

    private fun fetchCursorData(cursorId: Long): CursorSearchData {
        val cursorUser = userPort.findById(cursorId)
            ?: throw IllegalArgumentException("사용자 정보를 찾을 수 없습니다.")
        val school = cursorUser.schoolId.let { schoolPort.findById(it) }
            ?: throw IllegalArgumentException("학교 정보를 찾을 수 없습니다.")

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
            val schools = findSchool(user)
            UserResponse.from(user, schools.name)
        }
        return UserListResponse.from(users = userResponses, hasNext = hasNext)
    }

    private fun findSchool(user: User): School {
        return schoolPort.findById(user.schoolId)
            ?: throw IllegalArgumentException("학교 정보를 찾을 수 없습니다.")
    }

}
