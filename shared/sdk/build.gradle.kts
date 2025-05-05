plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.kotest.multiplatform)
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
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.datetime)
            }
        }
        val commonTest by getting {


        }
        val jsMain by getting {
            dependencies {
                implementation(project.dependencies.enforcedPlatform(libs.kotlin.wrappers.bom.get()))
                implementation(libs.kotlin.wrappers.js)
            }

            //Add the ksp generated folder so that it doesn't show errors in intellij
            //kotlin.srcDir(File("build/generated/ksp/js/jsMain/kotlin"))
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
            }
        }
    }
}

tasks.named<Jar>("jvmJar") {
    from(tasks.named("jandex"))
}