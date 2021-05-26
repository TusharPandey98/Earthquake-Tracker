package com.example.earthquakeapi.data.network

import com.example.earthquakeapi.model.Earthquake
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface EarthQuakeApi {

    @GET("1/query")
    suspend fun getEarthquakes(@QueryMap queries: Map<String,String>):Earthquake
    // query?format=geojson&minmagnitude=4.5&limit=1&orderby=magnitude/time

}