plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.ksp)
}

// Bloque 'android' principal
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

    // --- Configuración de TIPOS de COMPILACIÓN (Build Types) ---
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
            // IMPORTANTE: Aquí se asigna la configuración de firma 'release'
            // Debe estar DENTRO del bloque 'release { ... }'
            signingConfig = signingConfigs.getByName("release")
        }
    }

    // --- Configuración de FIRMA (Signing Configurations) ---
    // ESTE BLOQUE 'signingConfigs' DEBE ESTAR DIRECTAMENTE DENTRO del bloque 'android { ... }'
    // Al mismo nivel que 'defaultConfig' y 'buildTypes'
    signingConfigs {
        // Aquí creamos una nueva configuración de firma llamada "release"
        create("release") {
            // La ruta al archivo del keystore (que GitHub Actions decodificará)
            // Utilizamos '?: ""' para evitar posibles nulls en Kotlin si la variable no existe
            storeFile = file(System.getenv("KEYSTORE_FILE_PATH") ?: "")
            // Las contraseñas y el alias se obtienen de las variables de entorno
            // que GitHub Actions inyectará desde tus secretos
            storePassword = System.getenv("KEYSTORE_PASSWORD")
            keyAlias = System.getenv("KEY_ALIAS")
            keyPassword = System.getenv("KEY_PASSWORD")
        }
    }

    // --- Otras configuraciones de Android ---
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
