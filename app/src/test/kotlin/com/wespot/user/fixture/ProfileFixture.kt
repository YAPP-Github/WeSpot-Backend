package com.wespot.user.fixture

import com.wespot.user.Profile

object ProfileFixture {

    fun createWithId(
        id: Long
    ) = Profile(
        id = id,
        backgroundColor = "#FFFFFF",
        iconUrl = "https://icon.com",
    )

}
