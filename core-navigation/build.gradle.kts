// TODO: Remove once https://youtrack.jetbrains.com/issue/KTIJ-19369 is fixed
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("shivamogga.android.library")
    id("shivamogga.android.library.jacoco")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.ksp)
    id("shivamogga.spotless")
}

dependencies {
    api(libs.androidx.hilt.navigation.compose)
    api(libs.androidx.navigation.compose)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}