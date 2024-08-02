package com.wespot.user.domain

import com.wespot.user.UserClass
import com.wespot.user.fixture.UserFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.LocalDateTime

class UserClassTest : BehaviorSpec({

    given("사용자 정보에서") {
        val user = UserFixture.createWithSchoolIdAndGradeAndClassNumber(1, 1, 1)
        `when`("학교, 학년, 반 정보를") {
            val userClass = UserClass.of(user)
            then("정상적으로 뽑아낸다.") {
                userClass.schoolId shouldBe 1
                userClass.grade shouldBe 1
                userClass.classNumber shouldBe 1
            }
        }
    }

})
