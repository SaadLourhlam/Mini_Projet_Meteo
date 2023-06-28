package com.example.mini_projet_meteo.Api_Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherStackApi {
    @GET("current")
    Call<WeatherResponse> getCurrentWeather(@Query("access_key") String accessKey, @Query("query") String location);
}
