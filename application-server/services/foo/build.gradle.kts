plugins {
    id("io.quarkus")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("org.jetbrains.kotlin.plugin.jpa")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.kordamp.gradle.jandex")
}

val mockitoAgent = configurations.create("mockitoAgent")
dependencies {

    /// TODO: toggle between these two (same source code) to reproduce and fix
    implementation(libs.shared.sdk) // from external (composite build)
//    implementation(projects.sdkLocal) // from subproject in same build
    ///

    implementation(project.dependencies.enforcedPlatform(libs.quarkus.platform.bom))
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.quarkus.narayana.jta)
    implementation(libs.kotlinx.datetime)
    implementation(libs.vertx.lang.coroutines)
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.quarkus:quarkus-security")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-flyway")
    implementation("io.quarkus:quarkus-hibernate-orm-panache-kotlin")
    implementation("io.quarkus:quarkus-jdbc-mysql")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-micrometer")
    implementation("io.quarkus:quarkus-rest-kotlin-serialization")
    implementation("io.quarkus:quarkus-smallrye-context-propagation")
    implementation("io.quarkus:quarkus-smallrye-fault-tolerance")
    implementation("org.flywaydb:flyway-mysql")

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.quarkus:quarkus-junit5-mockito")
    testImplementation("io.rest-assured:kotlin-extensions")
    testImplementation("io.rest-assured:rest-assured")
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.core)
    testImplementation("io.quarkus:quarkus-test-security")
    mockitoAgent(libs.mockito.core) { isTransitive = false }
}

allOpen {
    annotation("io.quarkus.test.junit.QuarkusTest")
    annotation("jakarta.activation.DataHandler")
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("jakarta.enterprise.context.RequestScoped")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.ws.rs.Path")
}

noArg {
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
}

tasks.withType<Test> {
    dependsOn("jandex")
}

tasks.named("quarkusDependenciesBuild") {
    dependsOn("jandex")
}

tasks.withType<Test> {
    val agentJar = mockitoAgent.singleFile.absolutePath
    jvmArgs("-javaagent:$agentJar")
    jvmArgs("-XX:+EnableDynamicAgentLoading")
}