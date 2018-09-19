import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
  kotlin("jvm")
  id("java-library")
  id("maven-publish")
  jacoco
}

apply { from("../mvn-push.gradle") }

repositories {
  mavenLocal()
  jcenter()
  mavenCentral()
}

group = "io.github.tarek360"
version = "0.0.1"

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation(project(":core"))
  implementation(project(":rules"))
  implementation(project(":gitdiff-parser"))
  implementation(project(":githost"))
  implementation(project(":ci-detector"))
  testImplementation("junit:junit:4.12")
  testImplementation("org.mockito:mockito-core:2.22.0")
  testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.0.0-RC1")
  testImplementation("com.squareup.okhttp3:mockwebserver:3.11.0")
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}