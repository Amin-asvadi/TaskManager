plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.ksp)

}

android {
    namespace = "com.saba.data"
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
}

dependencies {
    implementation(project(":base-android"))
    implementation(project(":common-ui-resources"))
    libs.apply {
        implementation(work.runtime)
        implementation(work.manager)
        implementation(dagger.hilt.android)
        implementation(dagger.hilt.navigation)
        ksp(dagger.hilt.compiler)
        implementation(retrofit)
        implementation(libs.androidx.room.common)
        implementation(room.ktx)
        ksp(room.compiler)
        implementation(room.runtime)
        implementation(room.rxjava)
        implementation(room.rxjava3)
        implementation(adapter.rxjava2)
        implementation(logging.interceptor)
    }
    implementation(libs.android.data.store)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.kotlinx.serialization.converter)
    implementation(libs.kotlinx.serialization.json)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}