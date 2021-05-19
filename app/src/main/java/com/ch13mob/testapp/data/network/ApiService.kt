package com.ch13mob.testapp.data.network

import com.ch13mob.testapp.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

fun createPostApi(
    debug: Boolean = false,
    baseUrl: String = BuildConfig.API_BASE_URL
): PostApi {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(
            createOkHttpClient(debug = debug)
        )
        .addConverterFactory(GsonConverterFactory.create(createNetworkResponseGson()))
        .build()
        .create(PostApi::class.java)
}

private fun createOkHttpClient(debug: Boolean): OkHttpClient {
    return OkHttpClient.Builder().apply {
        addNetworkInterceptor(JsonRequestInterceptor())

        if (debug) {
            val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Timber.d(message)
                }
            })
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            addInterceptor(loggingInterceptor)
        }
    }.build()
}

private fun createNetworkResponseGson(): Gson {
    return GsonBuilder()
        .setLenient()
        .create()
}