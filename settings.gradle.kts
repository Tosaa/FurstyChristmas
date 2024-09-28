pluginManagement {
    listOf(repositories, dependencyResolutionManagement.repositories).forEach {
        it.apply {
            google()
            mavenCentral()
            gradlePluginPortal()
        }
    }
}


include("app")
rootProject.name = "FurstyChrismas"