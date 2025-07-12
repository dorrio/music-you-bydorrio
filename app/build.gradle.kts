plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "it.vfsfitvfm.vimusic"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.github.musicyou"
        minSdk = 21
        targetSdk = 35
        versionCode = 12
        versionName = "0.12"
    }

    splits {
        abi {
            reset()
            isUniversalApk = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // *** AÑADE ESTA LÍNEA PARA REFERENCIAR LA CONFIGURACIÓN DE FIRMA ***
            signingConfig signingConfigs.release
        }
    }

    // *** AÑADE ESTE BLOQUE signingConfigs ***
    signingConfigs {
        release {
            // Estas variables de entorno serán inyectadas por GitHub Actions
            // Asegúrate de que el nombre del archivo '.keystore' coincida
            // con el que decodificas en tu workflow de GitHub Actions (release.keystore en tu caso)
            storeFile file(System.getenv("KEYSTORE_FILE_PATH"))
            storePassword System.getenv("KEYSTORE_PASSWORD")
            keyAlias System.getenv("KEY_ALIAS")
            keyPassword System.getenv("KEY_PASSWORD")
        }
    }
    // *****************************************

    sourceSets.all {
        kotlin.srcDir("src/$name/kotlin")
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        freeCompilerArgs += "-Xcontext-receivers"
        jvmTarget = "17"
    }
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.activity)
    implementation(libs.coil.compose)
    implementation(libs.coil.network)
    implementation(libs.compose.material.icons)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)
    implementation(libs.compose.shimmer)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.util)
    implementation(libs.core.splashscreen)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.material.motion.compose)
    implementation(libs.media)
    implementation(libs.media3.exoplayer)
    implementation(libs.reorderable)
    implementation(libs.room)
    ksp(libs.room.compiler)
    implementation(projects.github)
    implementation(projects.innertube)
    implementation(projects.kugou)
    coreLibraryDesugaring(libs.desugaring)
}
