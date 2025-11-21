import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.gradleup.shadow)
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
    mainClass = "MainKt"
}

tasks.named<ShadowJar>("shadowJar") {
    archiveBaseName = project.name
    archiveClassifier = ""
    manifest {
        attributes("Main-Class" to "MainKt")
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
