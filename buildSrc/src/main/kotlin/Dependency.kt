object Dependency {
    object Kotlin {
        const val VERSION = "1.9.0"
    }

    object Coroutines {
        const val VERSION = "1.7.3"
    }

    object Serialization {
        const val VERSION = Dependency.Kotlin.VERSION
        object Json { const val VERSION = "1.6.0" }
    }
}