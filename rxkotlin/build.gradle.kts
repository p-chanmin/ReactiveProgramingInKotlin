import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("io.reactivex.rxjava2:rxkotlin:2.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.6.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}