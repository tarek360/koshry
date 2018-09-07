import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
  kotlin("jvm")
  application
}

application {
  mainClassName = "io.github.tarek360.app.AppKt"
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation(project(":koshry"))
  implementation(project(":rules"))
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}
