plugins {
    kotlin("jvm") version "1.9.23"
}

kotlin {
    jvmToolchain(21)
}

group = "pixels"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}