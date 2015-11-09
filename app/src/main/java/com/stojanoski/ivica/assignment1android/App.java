package com.stojanoski.ivica.assignment1android;

import android.app.Application;

import retrofit.RestAdapter;

/**
 * Created by sivic on 11/9/2015.
 */
public class App extends Application {

    private OpenWeatherService mOpenWeatherApi;

    @Override
    public void onCreate() {
        super.onCreate();

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(OpenWeatherService.API_URL)
                .build();
        mOpenWeatherApi = restAdapter.create(OpenWeatherService.class);
    }

    public OpenWeatherService getOpenWeatherApi() {
        return mOpenWeatherApi;
    }
}
