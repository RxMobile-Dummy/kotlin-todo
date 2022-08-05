plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
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
}

dependencies {

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("com.google.android.material:material:1.6.1")


    //import project
    implementation(project(":features:alarm-api"))
    implementation(project(":libraries:core"))
    implementation(project(":libraries:navigation"))
    implementation(project(":domain"))
    implementation(project(":features:preference"))
    implementation ("androidx.datastore:datastore-preferences:1.0.0-alpha01")
    implementation  ("androidx.datastore:datastore-core:1.0.0-alpha01")

    //logcat
    implementation("com.squareup.logcat:logcat:0.1")
    //hilt

    implementation ("com.google.dagger:hilt-android:2.42")
    kapt ("com.google.dagger:hilt-compiler:2.42")
    implementation( "androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("com.google.android.play:core:1.10.3")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}