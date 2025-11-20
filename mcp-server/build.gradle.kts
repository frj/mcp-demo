plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(dependencies.platform(libs.ktor.bom))
    implementation(libs.ktor.server.cio)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.sse)
    implementation(libs.logback)
    implementation(libs.mcp.kotlin.server)
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation(libs.junit.jupiter.engine)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "org.example.AppKt"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
