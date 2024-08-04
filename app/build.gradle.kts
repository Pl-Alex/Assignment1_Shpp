plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlin)
}


android {
    namespace = "com.alexP.assignment1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.alexP.assignment1"
        minSdk = 25
        targetSdk =  35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(":contactsprovider:data"))
    implementation(project(":textvalidation:data"))
    implementation(project(":datastore:data"))

    // AndroidX Core KTX for Kotlin extensions
    implementation(libs.androidx.ktx)
    implementation(libs.androidx.fragment.ktx)

    // AndroidX AppCompat for backward-compatible UI features
    implementation(libs.androidx.appcompat)

    // Material Design components for modern UI
    implementation(libs.material)

    // ConstraintLayout for flexible and responsive UI
    implementation(libs.constraintLayout)

    // Glide for efficient image loading and caching
    implementation(libs.glide)

    // JUnit for unit testing
    testImplementation(libs.junit)

    // AndroidX Test extensions for JUnit
    androidTestImplementation(libs.testExtJUnit)

    // Espresso for UI testing
    androidTestImplementation(libs.espressoCore)

    // Kotlin Coroutines for asynchronous programming
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    // DataStore for data storage using Kotlin Coroutines
    implementation(libs.datastorePreferences)

    // AndroidX Lifecycle components for managing UI-related data
    implementation(libs.lifecycleRuntime)
    implementation(libs.lifecycleViewmodel)

    // Card View
    implementation(libs.cardView)

}