plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
}

android {
    namespace = "com.example.supportservice"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.supportservice"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

configurations {
    create("cleanedAnnotations")
    implementation {
        exclude(group = "org.jetbrains", module = "annotations")
    }
}
dependencies {
//hilt
    implementation(libs.com.google.dagger.hilt.android)
    kapt(libs.com.google.dagger.hilt.android.compiler)
    kapt(libs.com.google.dagger.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    //Coroutines
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.test)
    //Room

    implementation(libs.room.ktx)
    implementation(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.paging)
//    implementation("org.jetbrains:annotations:23.0.0")
    //Retrofit
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit.base)
    implementation(libs.retrofit.gsonConverter)

    implementation (libs.accompanist.permissions)
    implementation(libs.android.acompanist.navigation)
    implementation (libs.accompanist.navigation.material)

    implementation(libs.chargemap.compose)
    implementation(libs.androidx.dataStore.preferences)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}