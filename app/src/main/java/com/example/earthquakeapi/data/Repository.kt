package com.example.earthquakeapi.data

import com.example.earthquakeapi.utils.RetrofitInstance
import com.example.earthquakeapi.model.Earthquake

class Repository() {

    suspend fun getEarthquake(applyQueries: HashMap<String, String>):Earthquake{
        return RetrofitInstance.api.getEarthquakes(applyQueries)
    }
}
