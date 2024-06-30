dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	compileOnly("org.springframework.boot:spring-boot-configuration-processor")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	implementation(project(":common"))
	implementation(project(":domain"))
	implementation(project(":core"))
	implementation(project(":infrastructure:mysql"))
}