plugins {
    kotlin("jvm") version "1.9.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.1")
    testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}