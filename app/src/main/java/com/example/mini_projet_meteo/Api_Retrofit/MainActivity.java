package com.example.mini_projet_meteo.Api_Retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.mini_projet_meteo.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "http://api.weatherstack.com/";
    private static final String ACCESS_KEY = "52f3269bf78f3254dd2a9a46de593c89";
    private TextView textTemperature;
    private TextView textWeatherDescription;
    private TextView textPressure;
    private TextView textHumidity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textTemperature = findViewById(R.id.text_temperature);
        textWeatherDescription = findViewById(R.id.text_weather_description);
        textPressure = findViewById(R.id.text_pressure);
        textHumidity = findViewById(R.id.text_humidity);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherStackApi weatherStackApi = retrofit.create(WeatherStackApi.class);

        Call<WeatherResponse> call = weatherStackApi.getCurrentWeather(ACCESS_KEY, "New York");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    if (weatherResponse != null) {
                        WeatherResponse.CurrentWeather currentWeather = weatherResponse.getCurrentWeather();
                        int temperature = currentWeather.getTemperature();
                        String weatherDescription = currentWeather.getWeatherDescription();
                        int pressure = currentWeather.getPressure();
                        int humidity = currentWeather.getHumidity();


                        textTemperature.setText("Temperature: " + temperature);
                        textWeatherDescription.setText("Weather Description: " + weatherDescription);
                        textPressure.setText("Pressure: " + pressure);
                        textHumidity.setText("Humidity: " + humidity);

                        // Do something with the weather data
                    }
                } else {
                    // Handle API error
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                // Handle network or request error
            }
        });
    }
}