package com.stojanoski.ivica.assignment1android;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by sivic on 11/9/2015.
 */
public interface OpenWeatherService {

    @GET("/data/2.5/group")
    void groupWeatherByIds(@Query("id") String ids, @Query("units") String units,
                           @Query("appid") String appId, Callback<GroupWeather> groupWeatherCallback);
}
