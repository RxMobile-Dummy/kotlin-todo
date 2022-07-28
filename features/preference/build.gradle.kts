plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion= "1.2.0"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("com.google.android.material:material:1.6.1")

    implementation ("androidx.compose.foundation:foundation:1.2.0-rc03")
    implementation ("androidx.compose.foundation:foundation-layout:1.2.0-rc03")
    implementation ("androidx.compose.animation:animation:1.2.0-rc03")
    implementation ("androidx.compose.runtime:runtime:1.2.0-rc03")
    implementation ("androidx.compose.runtime:runtime-livedata:1.2.0-rc03")
    implementation ("androidx.navigation:navigation-compose:2.4.0-alpha10")
    implementation ("androidx.compose.ui:ui-tooling:1.2.0-rc03")
    implementation ("androidx.compose.ui:ui:1.2.0-rc03")
    implementation ("androidx.compose.material:material:1.2.0-rc03")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.2.0-rc03")

    implementation(project(":domain"))
    implementation(project(":libraries:designsystem"))
    implementation(project(":libraries:core"))
    implementation(project(":data:repository"))


    //hilt
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation ("com.google.dagger:hilt-android:2.42")
    // kapt ("com.google.dagger:hilt-android-compiler:2.42")
    //kapt ("androidx.hilt:hilt-compiler:1.0.0")
    kapt ("com.google.dagger:hilt-compiler:2.42")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}