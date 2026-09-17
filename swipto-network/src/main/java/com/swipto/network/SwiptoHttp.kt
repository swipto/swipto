package com.swipto.network

import com.swipto.core.Outcome
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/** Network defaults that can be used without exposing client implementation details to screens. */
data class HttpConfig(
    val baseUrl: String,
    val connectTimeoutSeconds: Long = 30,
    val readTimeoutSeconds: Long = 30,
    val debugLogging: Boolean = false,
)

object SwiptoHttp {
    fun client(config: HttpConfig): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(config.connectTimeoutSeconds, TimeUnit.SECONDS)
        .readTimeout(config.readTimeoutSeconds, TimeUnit.SECONDS)
        .apply {
            if (config.debugLogging) addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
        }
        .build()

    fun retrofit(config: HttpConfig, client: OkHttpClient = client(config)): Retrofit = Retrofit.Builder()
        .baseUrl(config.baseUrl.ensureTrailingSlash())
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

suspend fun <T> networkCall(block: suspend () -> T): Outcome<T> = try {
    Outcome.Success(block())
} catch (error: Throwable) {
    Outcome.Failure(error)
}

private fun String.ensureTrailingSlash() = if (endsWith('/')) this else "$this/"
