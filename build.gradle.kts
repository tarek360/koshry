buildscript {
    val kotlinVersion = "1.2.60"
    repositories {
        mavenLocal()
        google()
        jcenter()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
    }
}

allprojects {
    repositories {
        mavenLocal()
        google()
        jcenter()
    }
}

apply { from("jacoco/jacocoFullReport.gradle") }