package com.example.book_icon

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookIconService {
    @GET("/v1/forecast")
    suspend fun getCurrentWeather(
        @Query("latitude")
        lat: Double,
        @Query("longitude")
        lon: Double,
        @Query("current_weather")
        currentWeather: Boolean = true
    ): CurrentWeather
}
