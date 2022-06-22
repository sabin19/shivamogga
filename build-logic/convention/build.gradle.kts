
plugins {
    `kotlin-dsl`
}

group = "com.sabin.shivamogga.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.spotless.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "shivamogga.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "shivamogga.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationJacoco") {
            id = "shivamogga.android.application.jacoco"
            implementationClass = "AndroidApplicationJacocoConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "shivamogga.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "shivamogga.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "shivamogga.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidLibraryJacoco") {
            id = "shivamogga.android.library.jacoco"
            implementationClass = "AndroidLibraryJacocoConventionPlugin"
        }
        register("androidTest") {
            id = "shivamogga.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
        register("spotless") {
            id = "shivamogga.spotless"
            implementationClass = "SpotlessConventionPlugin"
        }
    }
}
