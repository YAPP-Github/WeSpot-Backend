package com.wespot.user.port.out

import com.wespot.user.restriction.Restriction

interface RestrictionPort {

    fun save(restriction: Restriction): Restriction

}
