import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
  kotlin("jvm")
  id("java-library")
  id("maven-publish")
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
  implementation(project(":gitdiff-parser"))
  testImplementation(project(":core"))
  testImplementation("com.squareup.okhttp3:okhttp:3.8.1")
  testImplementation("junit:junit:4.12")
  testImplementation("org.mockito:mockito-core:2+")
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}