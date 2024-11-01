plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id ("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.mymovieapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mymovieapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    viewBinding{
        enable = true
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("androidx.fragment:fragment-ktx:1.8.4")

    //retrofit
    implementation(libs.retrofit) // Use the latest version
    implementation(libs.converter.gson)

    // room database
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    //Gson
    implementation(libs.gson)

    //Hilt
    implementation("com.google.dagger:hilt-android:2.50") // Replace 2.x with the latest version
    kapt("com.google.dagger:hilt-compiler:2.50")

    //ViewModel lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // coroutines
    implementation(libs.kotlinx.coroutines.core) // Core library
    implementation(libs.kotlinx.coroutines.android) // Android-specific library

    //Glide
    implementation("com.github.bumptech.glide:glide:4.15.0")
    kapt("com.github.bumptech.glide:compiler:4.15.0")

    //okHttp
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")

    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    //Gson
    implementation ("com.google.code.gson:gson:2.8.8")


}