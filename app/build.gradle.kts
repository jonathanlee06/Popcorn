import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.parcelize")
}

android {
    signingConfigs {
        getByName("debug") {
            storeFile = file("../keystore/Popcorn_keystore.jks")
            storePassword = "123456"
            keyAlias = "alias"
            keyPassword = "123456"
        }
        create("release") {
            storeFile = file("../keystore/Popcorn_keystore.jks")
            storePassword = "123456"
            keyAlias = "alias"
            keyPassword = "123456"
        }
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
            applicationIdSuffix = ".dev"
        }
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileSdk = AndroidConfig.compileSdk
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }
    defaultConfig {
        applicationId = AndroidConfig.applicationId
        buildConfigField(
            "String",
            "TMDB_API_KEY",
            "\"${getProperty("local.properties", "tmdb_api_key")}\""
        )
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk
        versionCode = AndroidConfig.versionCode
        versionName = AndroidConfig.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(Libraries.coreKtx)
    implementation(Libraries.appCompat)
    implementation(Libraries.material)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.scrollingIndicator)

    // architectural components
    implementation(Libraries.lifecycleExtensions)
    implementation(Libraries.lifecycleRuntimeKtx)
    implementation(Libraries.lifecycleJava8)
    implementation(Libraries.lifecycleViewModelCompose)

    // androidx
    implementation(Libraries.activityKtx)
    implementation(Libraries.fragmentKtx)
    implementation(Libraries.swipeRefreshLayout)
    implementation(Libraries.palette)

    // Jetpack Compose
    implementation(platform(Libraries.composeBom))
    implementation(Libraries.composeMaterial3)
    implementation(Libraries.composeUiToolingPreview)
    debugImplementation(Libraries.composeUiTooling)
    implementation(Libraries.composeActivity)
    implementation(Libraries.composeLiveData)

    // coroutines
    implementation(Libraries.coroutinesCore)
    implementation(Libraries.coroutinesAndroid)
    testImplementation(Libraries.coroutinesAndroid)
    testImplementation(Libraries.coroutinesTest)

    // Firebase
    implementation(platform(Libraries.firebaseBom))
    implementation(Libraries.firebaseAnalytics)
    implementation(Libraries.firebaseCrashlytics)

    // Hilt
    implementation(Libraries.hiltAndroid)
    kapt(Libraries.hiltCompiler)

    // network
    implementation(Libraries.okhttp)
    implementation(Libraries.retrofit)
    implementation(Libraries.gson)
    testImplementation(Libraries.okhttpTest)

    // Coil - image loader
    implementation(Libraries.coil)
    implementation(Libraries.coilCompose)

    // Material Dialog
    implementation(Libraries.materialDialog)

    // View Binding Delegate
    implementation(Libraries.bindingDelegate)

    // unit test
    testImplementation(Libraries.junit)
    testImplementation(Libraries.unitTestCore)
    testImplementation(Libraries.robolectric)
    testImplementation(Libraries.mockk)
    testImplementation(Libraries.androidxCoreTesting)
    androidTestImplementation(Libraries.espresso)
    androidTestImplementation(Libraries.androidxTest)
    androidTestImplementation(Libraries.androidxTestRules)
    androidTestImplementation(platform(Libraries.composeBom))
}

fun getProperty(fileName: String, propName: String): Any? {
    val propsFile = rootProject.file(fileName)
    return if (propsFile.exists()) {
        val props = Properties()
        props.load(FileInputStream(propsFile))
        if (props[propName] != null) {
            props[propName]
        } else {
            println("Not such property $propName in file $fileName")
        }
    } else {
        println("$fileName does not exist!")
    }
}