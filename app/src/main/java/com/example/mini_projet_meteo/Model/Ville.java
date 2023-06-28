package com.example.mini_projet_meteo.Model;

public class Ville {
    int temperature;
    String weatherDescription;
    int pressure;
    int humidity;

    public Ville() {
    }

    public Ville(int temperature, String weatherDescription, int pressure, int humidity) {
        this.temperature = temperature;
        this.weatherDescription = weatherDescription;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "Ville{" +
                "temperature=" + temperature +
                ", weatherDescription='" + weatherDescription + '\'' +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                '}';
    }
}
