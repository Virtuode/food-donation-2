plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.codinger"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.codinger"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.recyclerview)
    testImplementation(libs.junit)
    implementation (libs.circularimageview)
    implementation (libs.glide)
    annotationProcessor (libs.compiler)
    implementation (libs.viewpager2)
    implementation(platform(libs.firebase.bom))
    implementation (libs.facebook.android.sdk)
    implementation (libs.play.services.auth)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}