package net.a6te.lazycoder.forecastweather.weather.forecaste;

import net.a6te.lazycoder.forecastweather.MainActivity;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Programmer on 4/21/2017.
 */

public interface WeatherApi {
    @GET("data/2.5/forecast/daily?q=Dhaka&appid=a226ec225f23ea5717f7fa94ce785237")
    Call<WeatherForecastModelClass> getWeatherData();
}
