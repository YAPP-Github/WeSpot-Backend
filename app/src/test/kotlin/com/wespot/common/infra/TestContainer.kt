package com.wespot.common.infra

import org.springframework.boot.test.context.TestConfiguration
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName


@TestConfiguration
class TestContainer {

    companion object {
        private const val REDIS_DOCKER_IMAGE: String = "redis:7.0.8-alpine"

        private val REDIS_CONTAINER = GenericContainer(DockerImageName.parse(REDIS_DOCKER_IMAGE))
            .withExposedPorts(6379)
            .withReuse(true)

        init {
            REDIS_CONTAINER.start()

            System.setProperty("spring.data.redis.host", REDIS_CONTAINER.host)
            System.setProperty("spring.data.redis.port", REDIS_CONTAINER.getMappedPort(6379).toString())
        }

    }

}
