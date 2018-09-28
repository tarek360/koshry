import io.github.tarek360.dependencies.Dependencies
import io.github.tarek360.dependencies.Projects
import io.github.tarek360.dependencies.MainClasses
import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
  kotlin("jvm")
  application
}

application {
  mainClassName = MainClasses.appKit
}

dependencies {
  implementation(kotlin(Dependencies.kotlinJDK))
  implementation(project(Projects.koshry))
  implementation(project(Projects.rules))
  implementation(project(Projects.testCoverageRule))
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}
