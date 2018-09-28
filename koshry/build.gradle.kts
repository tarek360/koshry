import io.github.tarek360.dependencies.Dependencies
import io.github.tarek360.dependencies.MainApp
import io.github.tarek360.dependencies.Projects
import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
  kotlin("jvm")
  id("java-library")
  id("maven-publish")
  jacoco
}

apply { from("../push.gradle") }

repositories {
  mavenLocal()
  jcenter()
  mavenCentral()
}

group = MainApp.group
version = MainApp.version

dependencies {
  implementation(kotlin(Dependencies.kotlinJDK))
  implementation(project(Projects.core))
  implementation(project(Projects.rules))
  implementation(project(Projects.gitDiffParser))
  implementation(project(Projects.gitHost))
  implementation(project(Projects.ciDetector))
  testImplementation(Dependencies.junit)
  testImplementation(Dependencies.mockitoCore)
  testImplementation(Dependencies.mockitoKotlin)
  testImplementation(Dependencies.okHttp3Mock)
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}