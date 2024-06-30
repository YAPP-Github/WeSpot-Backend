import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {

    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":infrastructure:mysql"))
}

tasks.named<Jar>("jar") {
    enabled = true
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}