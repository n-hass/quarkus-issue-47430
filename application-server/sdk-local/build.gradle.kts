plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.kotest.multiplatform)
    alias(libs.plugins.ksp)
    alias(libs.plugins.jandex)
}

version = "1"
group = "au.com.me"

kotlin {
    explicitApi()
    jvm {
        withJava()
    }
    js(IR) {
        moduleName = "shared-sdk"
        generateTypeScriptDefinitions()
        nodejs {
            testTask {
                useMocha {
                    timeout = "30s"
                }
            }
        }
        browser {
            testTask {
                useMocha {
                    timeout = "30s"
                }
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project.dependencies.enforcedPlatform(libs.kotlinx.coroutines.bom.get()))
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.coroutines.core)
            }
        }
        val commonTest by getting {
            dependencies {
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(project.dependencies.enforcedPlatform(libs.kotlin.wrappers.bom.get()))
                implementation(project.dependencies.enforcedPlatform(libs.kotlinx.coroutines.bom.get()))
                implementation(libs.kotlinx.coroutines.coreJs)
                implementation(libs.kotlin.wrappers.js)
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }

        val jvmMain by getting {

        }

        val jvmTest by getting {
            dependencies {
                implementation(libs.kotest.runner.junit5)
            }
        }
    }
}

tasks.named<Jar>("jvmJar") {
    from(tasks.named("jandex"))
}