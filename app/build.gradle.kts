plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // Agrega otros plugins que necesites, por ejemplo:
    // id("kotlin-kapt")
    // id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.tudominio.tuapp" // Cambia por tu package name
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tudominio.tuapp" // Cambia por tu application ID
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            val keystoreFile = project.findProperty("KEYSTORE_FILE") as String?
            val keystorePassword = project.findProperty("KEYSTORE_PASSWORD") as String?
            val keyAliasProperty = project.findProperty("KEY_ALIAS") as String?
            val keyPasswordProperty = project.findProperty("KEY_PASSWORD") as String?
            
            if (!keystoreFile.isNullOrEmpty() && !keystorePassword.isNullOrEmpty() && 
                !keyAliasProperty.isNullOrEmpty() && !keyPasswordProperty.isNullOrEmpty()) {
                storeFile = file(keystoreFile)
                storePassword = keystorePassword
                keyAlias = keyAliasProperty
                keyPassword = keyPasswordProperty
            } else {
                // Configuración por defecto para desarrollo local
                storeFile = file("debug.keystore")
                storePassword = "android"
                keyAlias = "androiddebugkey"
                keyPassword = "android"
            }
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
        
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
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
        viewBinding = true // Si usas View Binding
        dataBinding = true // Si usas Data Binding
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    
    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    
    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")
    
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    
    // Networking (si usas Retrofit)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    
    // Image loading (si usas Coil)
    implementation("io.coil-kt:coil-compose:2.5.0")
    
    // Dependency Injection (si usas Hilt)
    // implementation("com.google.dagger:hilt-android:2.48")
    // kapt("com.google.dagger:hilt-compiler:2.48")
    // implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    
    // Room Database (si usas Room)
    // implementation("androidx.room:room-runtime:2.6.1")
    // implementation("androidx.room:room-ktx:2.6.1")
    // kapt("androidx.room:room-compiler:2.6.1")
    
    // Media playback (para tu app de música)
    implementation("androidx.media3:media3-exoplayer:1.2.1")
    implementation("androidx.media3:media3-ui:1.2.1")
    implementation("androidx.media3:media3-session:1.2.1")
    
    // Permissions
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")
    
    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    
    // Debug
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
