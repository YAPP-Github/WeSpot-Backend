dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    compileOnly("org.springframework.boot:spring-boot-configuration-processor")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.h2database:h2")

    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":core"))
    implementation(project(":infrastructure:mysql"))
    implementation(project(":infrastructure:redis"))
    implementation(project(":infrastructure:fcm"))
    implementation(project(":infrastructure:discord"))
    implementation(project(":infrastructure:s3"))

    // https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api
    testImplementation("io.jsonwebtoken:jjwt-api:0.11.2")
    testImplementation("io.jsonwebtoken:jjwt-impl:0.11.2")
    testImplementation("io.jsonwebtoken:jjwt-jackson:0.11.2")
    testImplementation("io.rest-assured:rest-assured")

}
