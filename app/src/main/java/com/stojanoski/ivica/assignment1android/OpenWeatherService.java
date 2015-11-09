package com.stojanoski.ivica.assignment1android;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by sivic on 11/9/2015.
 */
public interface OpenWeatherService {
    String API_URL = "http://api.openweathermap.org/";
    String UNITS = "metric";
    String APPID = "2de143494c0b295cca9337e1e96b00e0";

    @GET("/data/2.5/group")
    void groupWeatherByIds(@Query("id") String ids, @Query("units") String units,
                       @Query("appid") String appId, Callback<GroupWeather> groupWeatherCallback);

    @GET("/data/2.5/weather")
    void currentWeatherByCityName(@Query("q") String name, @Query("units") String units,
              @Query("appid") String appId, Callback<GroupWeather.CityWeather> openWeatherCallback);
}
