plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.apolloGraphql)
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.detekt)

}

detekt {
    config.setFrom(file("$rootDir/config/detekt/detekt.yml"))
}


android {
    namespace = "com.salah.falcon"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.salah.falcon"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    configurations.all {
        resolutionStrategy {
            force("androidx.test:monitor:1.7.2")
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "proguard-test-rules.pro"
            )
        }

        create("benchmark") {
            initWith(getByName("release"))
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks.add("release")
            // Only use benchmark Proguard rules
            proguardFiles("benchmarks-rules.pro")
        }

        release {
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // To publish on the Play store a private signing key is required, but to allow anyone
            // who clones the code to sign and run the release variant, use the debug signing key.
            // TODO: Abstract the signing configuration to a separate file to avoid hardcoding this.
            signingConfig = signingConfigs.named("debug").get()
            // Ensure Baseline Profile is fresh for release builds.
        }
    }

    // Test-specific configuration to prevent ProGuard issues
    testBuildType = "debug"
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions.jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
            excludes.add("META-INF/LICENSE.md")
            excludes.add("META-INF/LICENSE-notice.md")
        }
    }
}

apollo {
    service("service") {
        packageName.set("com.salah.falcon")
        introspection {
            endpointUrl.set("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // DI
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    // Networking
    implementation(libs.apollo)

    // Paging
    implementation(libs.androidx.paging.runtime)

    // Image loading
    implementation(libs.coil.compose)

    // Tools
    implementation(libs.kotlinx.serialization.json)

    // Compose Tools
    implementation(libs.compose.navigation)
    implementation(libs.compose.lifecycle)


    // Logging Tools
    implementation(libs.timber)
    implementation(libs.androidx.paging.compose.android)
    implementation(libs.androidx.profileinstaller)

    // Testing Tools
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.monitor)
//    androidTestImplementation(libs.compose.ui.test)

    androidTestImplementation(libs.mockk.android)
//    androidTestImplementation(libs.kotlinx.coroutines.test)
//    androidTestImplementation(libs.turbine)
//    androidTestImplementation(libs.paging.testing)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.uiautomator)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.robolectric)
    androidTestImplementation(libs.mockito.android)
    testImplementation(libs.mockito.kotlin)
    // Core common runtime
    implementation(libs.androidx.benchmark.common)

    // For micro-benchmark tests in androidTest
    androidTestImplementation(libs.androidx.benchmark.junit4)

    // For macro-benchmark tests in a dedicated module
    androidTestImplementation(libs.androidx.benchmark.macro.junit4)


}
