package com.example.earthquakeapi.model

import com.google.gson.annotations.SerializedName

data class Earthquake(

    @SerializedName("features")
    val features: List<Feature>
)

//    fun getMagnitude(): Double {
//        return magnitude
//    }
//
//    fun getLocation():String{
//        return location
//    }
//
//    fun getTimeInMilliseconds(): Long {
//        return timeInMilliseconds
//    }
//    fun getUrl(): String {
//        return url
//    }
