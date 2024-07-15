package com.wespot.vote.service

import com.wespot.DatabaseCleanup
import io.kotest.core.spec.style.AnnotationSpec.AfterEach
import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class VoteServiceTest(
    private var databaseCleanup: DatabaseCleanup
) : BehaviorSpec() {

    @AfterEach
    fun tearDown() {
        databaseCleanup.execute()
    }

    init {
        given("") {
            `when`("") {
                then("") {

                }
            }
        }
    }

}