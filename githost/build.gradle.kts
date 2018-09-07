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

val okHttpVersion = "3.8.1"

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation(project(":core"))

  implementation("com.squareup.okhttp3:logging-interceptor:$okHttpVersion")
  implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")

  implementation("org.json:json:20160810")
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}