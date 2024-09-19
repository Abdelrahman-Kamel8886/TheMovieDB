package com.abdok.themoviedb.Network

import com.abdok.themoviedb.Utils.Consts
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetroConnection {

    private val client = OkHttpClient.Builder().addInterceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer " + Consts.Token)
            .build()
        chain.proceed(newRequest)
    }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Consts.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val retroServices = retrofit.create(RetroServices::class.java)


}