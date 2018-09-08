package io.github.tarek360.githost.network

import io.github.tarek360.core.DEBUGGABLE
import io.github.tarek360.core.logger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


val okhttp: OkHttpClient by lazy {
    val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { m -> logger.d { m } })
    logger.level = HttpLoggingInterceptor.Level.BASIC
    val okhttpBuilder = OkHttpClient.Builder()
    if(DEBUGGABLE){
        okhttpBuilder.addInterceptor(logger)
    }
    okhttpBuilder.build()
}