import org.jetbrains.kotlin.gradle.dsl.JvmTarget
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

    implementation("${apiAddress}:paper-api:${Dependency.PaperAPI.VERSION}")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Dependency.Coroutines.VERSION}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Dependency.Serialization.Json.VERSION}")
}

val olderVersions = arrayOf(
    "1.17", "1.17.1",
    "1.18", "1.18.1", "1.18.2",
    "1.19", "1.19.1", "1.19.2", "1.19.3", "1.19.4",
    "1.20", "1.20.1", "1.20.2", "1.20.3", "1.20.4", "1.20.5", "1.20.6"
)

val javaVersion =
    if (olderVersions.contains(Dependency.Minecraft.VERSION)) "17"
    else "21"

java {
    toolchain {
        version = javaVersion
    }
}

@Suppress("PropertyName") val _group = group
tasks {
    test { useJUnitPlatform() }


    withType<KotlinCompile> {
        compilerOptions {
            jvmTarget.set(JvmTarget.valueOf("JVM_$javaVersion"))
        }
    }

    processResources {
        outputs.upToDateWhen { false }

        filesMatching("*.yml") {
            val pluginName = project.extra["plugin_name"] as String
            val packageName = project.extra["package_name"] as String

            expand(
                "pluginName" to pluginName, "group" to _group, "version" to version,
                "packageName" to packageName, "aliasName" to packageName.replaceFirstChar { it.uppercase() },

                "kotlinVersion" to Dependency.Kotlin.VERSION,
                "coroutineVersion" to Dependency.Coroutines.VERSION,
                "serializationVersion" to Dependency.Serialization.Json.VERSION,
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