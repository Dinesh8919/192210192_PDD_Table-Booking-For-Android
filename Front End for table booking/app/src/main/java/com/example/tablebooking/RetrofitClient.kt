package com.example.tablebooking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
     const val BASE_URL = "https://n070hp5k-80.inc1.devtunnels.ms/"

    // Custom Interceptor for adding headers
//    private val headerInterceptor = Interceptor { chain ->
//        val request: Request = chain.request().newBuilder()
//            .addHeader("Authorization", "Bearer YOUR_ACCESS_TOKEN")
//            .addHeader("Accept", "application/json")
//            .build()
//        chain.proceed(request)
//    }

    // Logging Interceptor
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Logs request and response body
    }

    // OkHttpClient with interceptors
    private val okHttpClient = OkHttpClient.Builder()
//        .addInterceptor(headerInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    // Retrofit instance
    private val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun api() :ApiService{
        return instance.create(ApiService::class.java)
    }
}