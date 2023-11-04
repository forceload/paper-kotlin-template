import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Dependency.Kotlin.VERSION
    kotlin("plugin.serialization") version Dependency.Serialization.VERSION
    application
}

group = "io.github.forceload"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Dependency.Coroutines.VERSION}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Dependency.Serialization.Json.VERSION}")
}

tasks {
    test { useJUnitPlatform() }
    withType<KotlinCompile> { kotlinOptions.jvmTarget = "17" }
}

application { mainClass.set("MainKt") }