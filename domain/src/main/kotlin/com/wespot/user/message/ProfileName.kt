package com.wespot.user.message

import com.wespot.common.ProfanityChecker
import com.wespot.exception.CustomException
import com.wespot.exception.ExceptionView
import com.wespot.user.User
import io.micrometer.common.util.StringUtils
import org.springframework.http.HttpStatus

class ProfileName(
    val name: String
) {

    companion object {
        fun of(name: String, users: List<User>, alreadyCreatedAnonymousProfile: List<AnonymousProfile>): ProfileName {
            val names = users
                .map { it.name }
                .toSet()

            if (StringUtils.isBlank(name)) {
                throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "익명 프로필로 설정할 이름은 존재해야합니다.")
            }

            if (ProfanityChecker.checkProfanity(name)) {
                throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "익명 프로필로에 비속어가 포함되어 있습니다.")
            }

            if (names.contains(name)) {
                throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "실명은 익명 프로필로 활용할 수 없습니다.")
            }

            if (alreadyCreatedAnonymousProfile.any { existsAnonymousProfile -> existsAnonymousProfile.name == name }) {
                throw CustomException(HttpStatus.BAD_REQUEST, ExceptionView.TOAST, "이미 해당 이름과 동일한 익명 프로필이 존재합니다.")
            }

            return ProfileName(name)
        }
    }

}
