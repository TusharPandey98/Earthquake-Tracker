package com.example.earthquakeapi.utils


import com.example.earthquakeapi.utils.Constants.Companion.BASE_URL
import com.example.earthquakeapi.data.network.EarthQuakeApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val  api: EarthQuakeApi by lazy {
        retrofit.create(EarthQuakeApi::class.java)
    }
}