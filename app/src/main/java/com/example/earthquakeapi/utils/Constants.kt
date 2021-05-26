package com.example.earthquakeapi.utils

class Constants {
    companion object{
        const val BASE_URL = "https://earthquake.usgs.gov/fdsnws/event/"
//        https://earthquake.usgs.gov/fdsnws/event/1/
        // https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&minmagnitude=4.5&limit=1

        // API Query Keys
        const val QUERY_FORMAT = "format"
        const val MINIMUM_MAGNITUDE = "minmagnitude"
        const val LIMIT = "limit"
        const val ORDER_BY = "orderby"


    }
}