plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp") version "1.6.10-1.0.2"
}

android {
    namespace = "com.timife.movies"
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = "com.timife.movies"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }


        val apiKey = project.properties["api_key"]
        buildConfigField("String", "API_KEY","\"$apiKey\"")

        val baseUrl = project.properties["base_url"]
        buildConfigField("String", "BASE_URL","\"$baseUrl\"")

        val imageBaseUrl = project.properties["image_base_url"]
        buildConfigField("String", "IMAGE_BASE_URL","\"$imageBaseUrl\"")
    }

    buildTypes {
        val release by getting {
            isMinifyEnabled = true
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packagingOptions {
        resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
}

dependencies {

    implementation(Deps.coreKtx)
    implementation (Deps.lifecycle)

    implementation (Deps.activityCompose)
    implementation (Deps.composeUi)
    implementation (Deps.composeTooling)
    implementation (Deps.composeMaterial)
    testImplementation (Deps.junitTest)
    androidTestImplementation (Deps.junitExt)
//    androidTestImplementation (Deps.espresso)
    androidTestImplementation (Deps.composeUiTest)
    debugImplementation (Deps.composeUiTest)
    debugImplementation (Deps.uiManifest)

    //di
    implementation(Deps.hilt)
    kapt(Deps.hiltAndroidCompiler)
    kapt (Deps.hiltCompiler)

    //Room
    implementation (Deps.room)
    implementation (Deps.roomKtx)
    kapt (Deps.roomCompiler)
    implementation (Deps.roomPaging)

    // Retrofit
    implementation (Deps.retrofit)
    implementation (Deps.moshi)
    implementation (Deps.okHttp)
    implementation (Deps.interceptor)
    implementation (Deps.coroutinesAdapter)
    implementation (Deps.jsonSerialize)

    // Compose dependencies
    implementation (Deps.viewModelCompose)
    implementation (Deps.materialIcons)
    implementation (Deps.flowLayout)
    implementation (Deps.pagingCompose)
    implementation (Deps.paging)
    implementation (Deps.swipeRefresh)
    implementation (Deps.hiltCompose)
    implementation (Deps.coilSvg)
    implementation (Deps.coilAccompanist)

    implementation (Deps.composeNavigation)
    implementation (Deps.composeLifecycle)

    // Coil
    implementation (Deps.coil)

    //Coroutines
    implementation (Deps.coroutineCore)
    implementation (Deps.coroutinesAndroid)
    implementation (Deps.coroutinesPlay)

    //Logging
    implementation (Deps.timber)

    // Chucker for analysing network traffic
    debugImplementation (Deps.chuckerLib)
    releaseImplementation (Deps.chuckerNoOp)
    //leak canary to detect memory leaks
    debugImplementation (Deps.canary)

    // Chucker for analysing network traffic
    debugImplementation (Deps.chuckerLib)
    releaseImplementation (Deps.chuckerNoOp)

    //Tests
    testImplementation (Deps.coroutinesTest)
    testImplementation (Deps.junitTest)
    testImplementation (Deps.coreTest)
    testImplementation (Deps.truth)
    testImplementation (Deps.turbine)
    testImplementation (Deps.mockk)
//    testImplementation (Deps.runner)

    //Instrumentation Tests
//    androidTestImplementation (Deps.espresso)
    androidTestImplementation (Deps.junitExt)
    androidTestImplementation (Deps.truth)
    androidTestImplementation (Deps.coroutinesTest)
    androidTestImplementation (Deps.coreTest)
}