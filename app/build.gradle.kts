plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.codinger"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.codinger"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


    }

    buildFeatures {
        buildConfig = true
    }
    buildTypes.configureEach {
        buildConfigField("String", "TOMTOM_API_KEY", "\"JvShheAVgqajf6D0tq8irLe2pLFL4DWX\"")
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
val tomtomApiKey: String by project

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.recyclerview)
    implementation(libs.play.services.nearby)
    implementation(libs.transport.api)
    testImplementation(libs.junit)
    implementation(libs.circularimageview)
    implementation(libs.glide)
    annotationProcessor(libs.compiler)
    implementation(libs.viewpager2)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.denzcoskun.imageslideshow)
    implementation(platform(libs.firebase.bom))
    implementation(libs.facebook.android.sdk)
    implementation(libs.map.display)
    implementation(libs.provider.android)
    implementation(libs.provider.gms)
    implementation(libs.provider.proxy)
    implementation(libs.provider.api)
    implementation(libs.route.planner.online)
    implementation(libs.play.services.maps)
    implementation(libs.provider.simulation)
    implementation (libs.play.services.location)
    implementation(libs.play.services.auth)
    implementation(libs.play.services.location)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}