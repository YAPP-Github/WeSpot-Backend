import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("plugin.jpa") version "1.9.24"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
}

extra["springCloudVersion"] = "2023.0.2"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

allprojects {
    group = "com.wespot"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }
}


subprojects {
    apply(plugin = "java")

    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "kotlin-noarg")
    apply(plugin = "kotlin-jpa")

    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")

    dependencies {

        // retry
        implementation("org.springframework.retry:spring-retry")
        implementation("org.springframework.boot:spring-boot-starter-aop")

        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.3.0")
        implementation("io.swagger.core.v3:swagger-core:2.2.19")

        implementation("javax.xml.bind:jaxb-api:2.3.0")

        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-security")
        implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
        implementation("com.amazonaws:aws-java-sdk-s3")

        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("com.google.firebase:firebase-admin:9.2.0")

        // aws
        implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")
        implementation("com.amazonaws:aws-java-sdk-s3:1.12.767")
        implementation("software.amazon.awssdk:s3:2.27.3")
        implementation("software.amazon.awssdk:s3control:2.27.3")
        implementation("software.amazon.awssdk:s3outposts:2.27.3")


        implementation("org.springframework.boot:spring-boot-starter-security")

        // https://mvnrepository.com/artifact/org.bouncycastle/bcpkix-jdk15on
        implementation("org.bouncycastle:bcpkix-jdk15on:1.69")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")

        testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
        testImplementation("io.kotest:kotest-assertions-core:5.8.0")
        testImplementation("io.kotest:kotest-property:5.8.0")
        testImplementation("io.mockk:mockk:1.13.12")

        testImplementation("org.testcontainers:testcontainers:1.19.0")
        testImplementation("org.testcontainers:junit-jupiter:1.19.0")

        testImplementation("org.awaitility:awaitility-kotlin:4.2.0")

    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "21"
        }
    }

    tasks.withType<Test> {
        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
            events = setOf(TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)
        }
        useJUnitPlatform()
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }
}
