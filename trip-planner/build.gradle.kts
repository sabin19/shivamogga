plugins {
    id("shivamogga.android.library")
    id("shivamogga.android.feature")
    id("shivamogga.android.library.compose")
    id("shivamogga.android.library.jacoco")
    id("dagger.hilt.android.plugin")
    id("shivamogga.spotless")
    id("shivamogga.map.secret")
}
dependencies{
    implementation(libs.google.map)
    implementation(libs.compose.map)
}
