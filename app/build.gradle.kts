import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
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

    buildFeatures.viewBinding = true
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

dependencies {
    implementation(Libraries.coreKtx)
    implementation(Libraries.appCompat)
    implementation(Libraries.material)
    implementation(Libraries.constraintLayout)

    // architectural components
    implementation(Libraries.lifecycleExtensions)
    implementation(Libraries.lifecycleRuntimeKtx)
    implementation(Libraries.lifecycleJava8)

    // androidx
    implementation(Libraries.swipeRefreshLayout)
    implementation(Libraries.palette)

    // coroutines
    implementation(Libraries.coroutinesCore)
    implementation(Libraries.coroutinesAndroid)
    testImplementation(Libraries.coroutinesAndroid)
    testImplementation(Libraries.coroutinesTest)

    // Firebase
    implementation(platform(Libraries.firebaseBom))
    implementation(Libraries.firebaseAnalytics)
    implementation(Libraries.firebaseCrashlytics)

    // network
    implementation(Libraries.okhttp)
    implementation(Libraries.retrofit)
    implementation(Libraries.gson)
    testImplementation(Libraries.okhttpTest)

    // Coil - image loader
    implementation(Libraries.coil)

    // Material Dialog
    implementation(Libraries.materialDialog)

    // unit test
    testImplementation(Libraries.junit)
    testImplementation(Libraries.unitTestCore)
    testImplementation(Libraries.robolectric)
    testImplementation(Libraries.mockk)
    testImplementation(Libraries.androidxCoreTesting)
    androidTestImplementation(Libraries.espresso)
    androidTestImplementation(Libraries.androidxTest)
    androidTestImplementation(Libraries.androidxTestRules)
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