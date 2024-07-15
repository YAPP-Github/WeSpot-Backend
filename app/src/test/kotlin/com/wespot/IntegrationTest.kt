package com.wespot

import io.restassured.RestAssured
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest {

    @LocalServerPort
    private var port: Int? = null

    @Autowired
    private var databaseCleanup: DatabaseCleanup? = null

    @BeforeEach
    fun setUp() {
        RestAssured.port = port!!
    }

    @AfterEach
    fun tearDown() {
        databaseCleanup!!.execute()
    }

}