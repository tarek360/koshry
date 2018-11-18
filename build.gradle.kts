import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.2.71"
}

buildscript {
    repositories {
        mavenLocal()
        google()
        jcenter()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.2.71"))
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

dependencies {
    compile(kotlin("stdlib-jdk8"))
}

repositories {
    mavenCentral()
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}