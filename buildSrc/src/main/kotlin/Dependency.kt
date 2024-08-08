object Dependency {
    object Kotlin {
        const val VERSION = "2.0.0"
    }

    object Coroutines {
        const val VERSION = "1.9.0-RC"
    }

    object Minecraft {
        const val VERSION = "1.21"
        const val API_VERSION = "1.21"
    }

    object PaperAPI { const val VERSION = "${Minecraft.VERSION}-R0.1-SNAPSHOT" }

    object Serialization {
        const val VERSION = Dependency.Kotlin.VERSION
        object Json { const val VERSION = "1.7.1" }
    }
}