pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        jcenter()
//        maven { "https://jitpack.io" } // Add this line

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
//        jcenter

//        jcenter()
//        jcenter()

        mavenCentral()
        jcenter()

//        maven {"https://jitpack.io"}
//        maven { "https://jcenter.bintray.com" }
//        maven(url = "https://jcenter.bintray.com");

    }
}

rootProject.name = "My Application"
include(":app")
