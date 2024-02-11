package com.irsyad.filmku.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private var apiKey: String? = null

    fun getApiService(): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (isDebugBuild()) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }

    fun setApiKey(apiKey: String) {
        this.apiKey = apiKey
    }

    fun getApiKey(): String {
        if (apiKey.isNullOrEmpty()) {
            throw IllegalStateException("API key has not been set. Call setApiKey() first.")
        }
        return apiKey!!
    }

    private fun isDebugBuild(): Boolean {
        // Implement your logic to check whether the app is running in debug mode
        // For example, you can use a context reference or a boolean flag
        return true // Assuming it's always in debug mode for this example
    }
}