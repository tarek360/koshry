import io.github.tarek360.dependencies.Dependencies
import io.github.tarek360.dependencies.Modules
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
  implementation(project(Modules.koshry))
  implementation(project(Modules.rules))
  implementation(project(Modules.testCoverageRule))
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}
