package com.wespot.common.service

import com.wespot.DatabaseCleanup
import com.wespot.common.infra.TestContainer
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestContainer::class)
@SpringBootTest
class ServiceTest {

    @Autowired
    private lateinit var databaseCleanup: DatabaseCleanup

    @AfterEach
    fun tearDown() {
        databaseCleanup.execute()
    }

}
