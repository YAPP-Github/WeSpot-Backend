package com.wespot.user.port.out

import com.wespot.user.UserPolicyAgreement

interface UserPolicyAgreementPort {

    fun save(userPolicyAgreement: UserPolicyAgreement): UserPolicyAgreement

}
