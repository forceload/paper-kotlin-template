import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Dependency.Kotlin.VERSION
    kotlin("plugin.serialization") version Dependency.Serialization.VERSION
}

group = "io.github.forceload"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

val frequentLegacies = arrayOf("1.12", "1.12.2", "1.13", "1.16", "1.16.5")
dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))

    val apiAddress =
        if (!frequentLegacies.contains(Dependency.Minecraft.VERSION)) "io.papermc.paper"
        else "com.destroystokyo.paper"

    compileOnly("${apiAddress}:paper-api:${Dependency.PaperAPI.VERSION}")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Dependency.Coroutines.VERSION}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Dependency.Serialization.Json.VERSION}")
}

tasks {
    test { useJUnitPlatform() }
    withType<KotlinCompile> { kotlinOptions.jvmTarget = "17" }

    processResources {
        filesMatching("*.yml") {
            outputs.upToDateWhen { false }
            val pluginName = project.extra["plugin_name"] as String
            val packageName = project.extra["package_name"] as String

            expand(
                "pluginName" to pluginName, "group" to group,
                "packageName" to packageName, "aliasName" to packageName.replaceFirstChar { it.uppercase() },

                "kotlinVersion" to Dependency.Kotlin.VERSION,
                "coroutineVersion" to Dependency.Coroutines.VERSION,
                "serializationVersion" to Dependency.Serialization.VERSION,
                "apiVersion" to Dependency.Minecraft.API_VERSION
            )
        }
    }

    register<Jar>("pluginJar") {
        archiveBaseName.set(rootProject.name)
        archiveClassifier.set("")

        from(sourceSets["main"].output)
    }
}