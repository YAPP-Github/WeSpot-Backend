import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {

    // redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":core"))
}

tasks.named<Jar>("jar") {
    enabled = true
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}