plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotest.multiplatform) apply false
    alias(libs.plugins.kotlin.plugin.allopen) apply false
    alias(libs.plugins.kotlin.plugin.jpa) apply false
    alias(libs.plugins.kotlin.plugin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.quarkus) apply false
    alias(libs.plugins.jandex) apply false
}

