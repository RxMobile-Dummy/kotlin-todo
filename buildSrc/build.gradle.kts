import org.gradle.kotlin.dsl.`kotlin-dsl`

repositories {
    google()
    mavenCentral()
    maven {
        url = uri("https://maven.google.com")
    }

}


plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

