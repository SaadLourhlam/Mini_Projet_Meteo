package com.example.mini_projet_meteo.Api_Retrofit;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("current")
    private CurrentWeather currentWeather;

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }
// Getters and setters

    public class CurrentWeather {

        public int getTemperature() {
            return temperature;
        }

        public String getWeatherDescription() {
            return weatherDescription;
        }

        public int getPressure() {
            return pressure;
        }

        public int getHumidity() {
            return humidity;
        }

        @SerializedName("temperature")
        private int temperature;

        @SerializedName("weather_descriptions")
        private String weatherDescription;

        @SerializedName("pressure")
        private int pressure;

        @SerializedName("humidity")
        private int humidity;

        // Getters and setters
    }
}
