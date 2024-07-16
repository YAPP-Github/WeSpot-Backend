import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.data:spring-data-commons")

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
