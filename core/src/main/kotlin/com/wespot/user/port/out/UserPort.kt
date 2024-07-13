package com.wespot.user.port.out

import com.wespot.user.User
import org.springframework.stereotype.Repository

@Repository
interface UserPort { // TODO: 자기야 이거 충돌좀 각오 해야하긴 할 것 같아..

    fun findAllBySchoolIdAndGradeAndGroupNumber(
        schoolId: Long,
        grade: Int,
        groupNumber: Int
    ): User; // TODO: 자기야 일단, 그냥 작업해놓을 테니까 나중에 Mapper로 바꿔줘

}