import io.github.tarek360.dependencies.Dependencies
import io.github.tarek360.dependencies.Project
import io.github.tarek360.dependencies.Modules
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

group = Project.group
version = Project.version

dependencies {
  implementation(kotlin(Dependencies.kotlinJDK))
  implementation(project(Modules.rules))
  testImplementation(project(Modules.rulesTest))
  testImplementation(Dependencies.junit)
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}