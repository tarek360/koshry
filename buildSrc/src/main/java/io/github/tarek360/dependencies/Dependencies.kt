package io.github.tarek360.dependencies

object Dependencies {

    var kotlinJDK       = "stdlib-jdk8"

    var json            = "org.json:json:${Versions.json}"

    val junit           = "junit:junit:${Versions.junit}"

    val mockitoCore     = "org.mockito:mockito-core:${Versions.mockitoCore}"

    val mockitoKotlin   = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"

    val okHttp3Mock     = "com.squareup.okhttp3:mockwebserver:${Versions.okHttp3}"

    val okHttp3         = arrayOf(
            "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp3}",
            "com.squareup.okhttp3:okhttp:${Versions.okHttp3}")

}