pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
//    versionCatalogs {
//        create("libs") {
//            from(files("gradle/lib.versions.toml"))
//        }
//    }
}

rootProject.name = "Assignment1"
include(":app")
include(":textvalidation:data")
include(":datastore:data")
include(":contactsprovider:data")
