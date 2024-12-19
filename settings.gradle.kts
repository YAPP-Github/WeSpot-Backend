plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "wespot"
include(
    "domain",
    "core",
    "infrastructure:discord",
    "infrastructure:mysql",
    "infrastructure:redis",
    "infrastructure:fcm",
    "infrastructure:s3",
    "app",
    "common",
)
include("infrastructure:s3")
findProject(":infrastructure:s3")?.name = "s3"
