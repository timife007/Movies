// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id ("com.android.application") version Versions.buildGradle apply false
    id ("com.android.library") version Versions.buildGradle apply false
    id ("org.jetbrains.kotlin.android") version Versions.kotlin apply false
    id ("com.google.dagger.hilt.android") version "2.44" apply false
    id ("com.android.test") version Versions.buildGradle apply false
}

tasks.register(name = "type", type = Delete::class) {
    delete(rootProject.buildDir)
}