buildscript {
    val kotlinVersion = "1.2.60"
    repositories {
        mavenLocal()
        google()
        jcenter()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.1")
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
apply { from("auto_release.gradle.kts") }
