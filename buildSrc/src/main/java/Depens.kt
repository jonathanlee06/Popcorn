object AndroidConfig {
    const val applicationId = "com.jonathanlee.popcorn"
    const val compileSdk = 33
    const val minSdk = 23
    const val targetSdk = 33
    const val versionCode = 3
    const val versionName = "1.2"
}

object Versions {
    const val gradle = "7.4.2"

    // Architectural Components
    const val activityKtx = "1.7.0"
    const val fragmentKtx = "1.5.6"
    const val material = "1.8.0"
    const val lifecycle = "2.6.1"
    const val recyclerView = "1.3.0"

    // Coil - Image Loader
    const val coil = "2.3.0"

    const val composeBom = "2023.03.00"

    // Google
    const val firebase = "2.7.1"
    const val googleServices = "4.3.10"

    // Hilt
    const val hilt = "2.44"

    // Kotlin
    const val kotlin = "1.8.10"
    const val coroutines = "1.6.4"

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
    const val appCompat = "androidx.appcompat:appcompat:1.6.1"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.3"
    const val coreKtx = "androidx.core:core-ktx:1.10.0"
    const val palette = "androidx.palette:palette-ktx:1.0.0"
    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

    // Architectural Components
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtx}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    const val lifecycleJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val lifecycleViewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"

    // Coil - Image Loader
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val coilCompose = "io.coil-kt:coil-compose:${Versions.coil}"

    // Jetpack Compose
    const val composeActivity = "androidx.activity:activity-compose:1.6.1"
    const val composeBom = "androidx.compose:compose-bom:${Versions.composeBom}"
    const val composeLiveData = "androidx.compose.runtime:runtime-livedata"
    const val composeMaterial3 = "androidx.compose.material3:material3"
    const val composeMaterial2 = "androidx.compose.material:material"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    const val composeUiTest = "androidx.compose.ui:ui-test-junit4"
    const val composeUiTestManifest = "androidx.compose.ui:ui-test-manifest"

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

    // Hilt
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"

    // Kotlin
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    // Material Dialog
    const val materialDialog = "com.afollestad.material-dialogs:core:${Versions.materialDialog}"

    // Network
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val retrofit = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val okhttp = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val okhttpTest = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp}"

    // Scrolling indicator
    const val scrollingIndicator =
        "ru.tinkoff.scrollingpagerindicator:scrollingpagerindicator:1.0.6"

    // View Binding Delegate
    const val bindingDelegate = "com.github.jonathanlee06:BindingDelegate:1.0.3"

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