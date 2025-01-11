plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.saba.base_android"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    libs.apply {
        implementation(dagger.hilt.android)
        implementation(dagger.hilt.navigation)
        ksp(dagger.hilt.compiler)
        implementation(libs.retrofit)
        implementation(compose.coil)
        implementation(lifecycle.runtime.compose)
        implementation(androidx.ui)
        implementation(androidx.ui.graphics)
        implementation(converter.gson)
        implementation(androidx.ui.tooling.preview)
        implementation(platform(androidx.compose.bom))
        implementation(androidx.material3)
        implementation(androidx.core.ktx)
        implementation(androidx.appcompat)
        implementation(kotlinx.serialization.converter)
        implementation(kotlinx.serialization.json)
        implementation(androidx.core.ktx)
        implementation(androidx.appcompat)
        implementation(material)
    }
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}