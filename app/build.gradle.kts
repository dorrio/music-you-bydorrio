plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.ksp)
    // Descomenta la siguiente línea si usas kotlinx.serialization directamente en este módulo
    // y tienes el plugin definido en tu libs.versions.toml
    // alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "it.vfsfitvnm.vimusic"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.github.musicyou"
        minSdk = 21
        targetSdk = 35
        versionCode = 12
        versionName = "0.12"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // Añadido para completitud
        vectorDrawables.useSupportLibrary = true // Añadido para completitud
    }

    signingConfigs {
        create("release") {
            val storeFileEnv = System.getenv("KEYSTORE_FILE_PATH")
            val storePasswordEnv = System.getenv("KEYSTORE_PASSWORD")
            val keyAliasEnv = System.getenv("KEY_ALIAS")
            val keyPasswordEnv = System.getenv("KEY_PASSWORD")

            if (storeFileEnv != null && storePasswordEnv != null && keyAliasEnv != null && keyPasswordEnv != null) {
                storeFile = file(storeFileEnv)
                storePassword = storePasswordEnv
                keyAlias = keyAliasEnv
                keyPassword = keyPasswordEnv
            } else if (System.getenv("CI") == null) { // Solo muestra advertencia si NO estamos en CI
                println(
                    """
                    Advertencia local: No se encontraron todas las variables de entorno para la firma de release.
                    (KEYSTORE_FILE_PATH, KEYSTORE_PASSWORD, KEY_ALIAS, KEY_PASSWORD).
                    El APK de release no será firmado si estas variables no están disponibles.
                    Para builds de CI, se espera que estas variables estén siempre presentes.
                    """.trimIndent()
                )
            }
            // En CI, si las variables no están, el build fallará cuando intente usar una config de firma inválida.
        }
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
            isDebuggable = true // Por defecto es true para debug, pero explícito no hace daño
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    sourceSets.all {
        kotlin.srcDir("src/$name/kotlin")
    }

    buildFeatures {
        compose = true
    }

    // composeOptions {
    // La versión del compilador de Compose debería ser manejada por el plugin
    // org.jetbrains.kotlin.plugin.compose y su versión alineada con Kotlin.
    // Si necesitas especificarla explícitamente, descomenta y ajusta:
    // kotlinCompilerExtensionVersion = "COMPATIBLE_VERSION_WITH_KOTLIN_2_1_20"
    // }

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
    implementation(libs.compose.ui.util) // ui-util
    implementation(libs.core.splashscreen)
    implementation(libs.lifecycle.viewmodel.compose) // lifecycle-viewmodel-compose
    implementation(libs.material.motion.compose) // material-motion-compose
    implementation(libs.media)
    implementation(libs.media3.exoplayer)
    implementation(libs.reorderable)
    implementation(libs.room) // room-runtime
    ksp(libs.room.compiler) // room-compiler

    implementation(projects.github)
    implementation(projects.innertube)
    implementation(projects.kugou)

    coreLibraryDesugaring(libs.desugaring)

    // Dependencias de Testing (Añade alias a tu libs.versions.toml si no los tienes)
    // testImplementation("junit:junit:4.13.2")
    // androidTestImplementation("androidx.test.ext:junit:1.2.1")
    // androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    // androidTestImplementation(platform(libs.compose.bom))
    // debugImplementation(libs.compose.ui.tooling) // Para previews y layout inspector
    // debugImplementation(libs.compose.ui.test.manifest) // Para tests de UI
    // androidTestImplementation(libs.compose.ui.test.junit4) // Si tienes un alias para compose.ui.test.junit4
}
