object AndroidConfig {
    const val applicationId = "com.jonathanlee.popcorn"
    const val compileSdk = 31
    const val minSdk = 23
    const val targetSdk = 31
    const val versionCode = 2
    const val versionName = "1.1"
}

object Versions {
    const val gradle = "7.1.2"

    // Architectural Components
    const val material = "1.4.0"
    const val lifecycle = "2.2.0"

    // Coil - Image Loader
    const val coil = "2.1.0"

    // Google
    const val firebase = "2.7.1"
    const val googleServices = "4.3.10"

    // Kotlin
    const val kotlin = "1.5.21"
    const val coroutines = "1.5.1"

    // Material Dialog
    const val materialDialog = "3.3.0"

    // Network
    const val retrofit = "2.9.0"
    const val okhttp = "4.9.0"
    const val gson = "2.8.7"

    // Unit Test
    const val junit = "4.13.2"
    const val androidxTest = "1.1.3"
    const val unitTestCore = "1.4.0"
    const val mockk = "1.12.0"
    const val robolectric = "4.7.3"
    const val androidxTestRules = "1.2.0-alpha04"
    const val espresso = "3.2.0"
    const val androidxCoreTesting = "2.1.0"
}

object Libraries {
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"

    // Android
    const val appCompat = "androidx.appcompat:appcompat:1.4.1"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.3"
    const val coreKtx = "androidx.core:core-ktx:1.7.0"
    const val palette = "androidx.palette:palette-ktx:1.0.0"
    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

    // Architectural Components
    const val material = "com.google.android.material:material:${Versions.material}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val lifecycleJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"

    // Coil - Image Loader
    const val coil = "io.coil-kt:coil:${Versions.coil}"

    // Coroutines
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"

    // Google
    const val firebase = "com.google.firebase:firebase-crashlytics-gradle:${Versions.firebase}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
    const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx"
    const val firebaseBom = "com.google.firebase:firebase-bom:28.4.1"
    const val googleServices = "com.google.gms:google-services:${Versions.googleServices}"

    // Kotlin
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    // Material Dialog
    const val materialDialog = "com.afollestad.material-dialogs:core:${Versions.materialDialog}"

    // Network
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val retrofit = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val okhttp = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val okhttpTest = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp}"

    // Unit Test
    const val junit = "junit:junit:${Versions.junit}"
    const val androidxTest = "androidx.test.ext:junit:${Versions.androidxTest}"
    const val unitTestCore = "androidx.test:core:${Versions.unitTestCore}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
    const val androidxTestRules = "androidx.test:rules:${Versions.androidxTestRules}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val androidxCoreTesting =
        "androidx.arch.core:core-testing:${Versions.androidxCoreTesting}"
}