import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")

}

android {
    namespace = "com.nerodev.blockbuster"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.nerodev.blockbuster"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        //region MoviesTMDB Access Token
        val properties = Properties().apply {
            load(rootProject.file("key.properties").reader())
        }

        val key:String = properties.getProperty("API_KEY") ?: ""
        buildConfigField("String", "API_KEY", "\"$key\"")
        //endregion
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.7.6")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$rootProject.composeVersion")

    // testing
    testImplementation(libs.mockk)
    testImplementation(libs.androidx.core.testing)

    // 📦 Dependencia necesaria
    implementation("androidx.compose.material3:material3-window-size-class:1.3.1")
    // Jetpack Compose integration
    implementation(libs.androidx.navigation.compose)
    // Testing Navigation
    androidTestImplementation(libs.androidx.navigation.testing)

    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    //Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    // Moshi
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)

    // OkHttp
    implementation(libs.logging.interceptor)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    //Coil
    implementation("io.coil-kt.coil3:coil-compose:3.0.4")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.4")

}