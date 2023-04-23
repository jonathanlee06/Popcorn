// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Libraries.googleServices)
        classpath(Libraries.firebase)
        classpath(Libraries.gradle)
        classpath(Libraries.kotlinGradlePlugin)
        classpath(Libraries.hiltGradlePlugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}