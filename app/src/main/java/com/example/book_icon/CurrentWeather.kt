package com.example.book_icon


// {"latitude":35,"longitude":139.625,"generationtime_ms":0.2410411834716797,"utc_offset_seconds":0,"timezone":"GMT","timezone_abbreviation":"GMT","elevation":0,"current_weather_units":{"time":"iso8601","interval":"seconds","temperature":"°C","windspeed":"km\/h","winddirection":"°","is_day":"","weathercode":"wmo code"},"current_weather":{"time":"2024-08-05T09:45","interval":900,"temperature":27.8,"windspeed":5.6,"winddirection":140,"is_day":0,"weathercode":1}}
data class CurrentWeather(
    val latitude: Int,
    val longitude: Double,
    val generationtime_ms: Double,
    val utc_offset_seconds: Int,
    val timezone: String,
    val timezone_abbreviation: String,
    val elevation: Int,
    val current_weather_units: CurrentWeatherUnits,
    val current_weather: CurrentWeatherData

)

data class CurrentWeatherUnits(
    val time: String,
    val interval: String,
    val temperature: String,
    val windspeed: String,
    val winddirection: String,
    val is_day: String,
    val weathercode: String
)

data class CurrentWeatherData(
    val time: String,
    val interval: Int,
    val temperature: Double,
    val windspeed: Double,
    val winddirection: Int,
    val is_day: Int,
    val weathercode: Int
)
