package com.stojanoski.ivica.assignment1android;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CityInfoActivity extends AppCompatActivity {

    protected static final String EXTRA_INFO = "info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String info = getIntent().getStringExtra(EXTRA_INFO);
        getCityByName(info);
    }

    private void getCityByName(String cityName) {
        App mApp = ((App) getApplication());
        OpenWeatherService mOpenWeatherApi = mApp.getOpenWeatherApi();

        mOpenWeatherApi.currentWeatherByCityName(cityName, OpenWeatherService.UNITS,
                OpenWeatherService.APPID, new Callback<GroupWeather.CityWeather>() {
                    @Override
                    public void success(GroupWeather.CityWeather cityWeather, Response response) {
                        showCityInfo(cityWeather);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }

    private void showCityInfo(GroupWeather.CityWeather info) {
        TextView cityName = (TextView) findViewById(R.id.textViewCityName);
        cityName.setText(info.getName());

        TextView temperature = (TextView) findViewById(R.id.textViewTemperature);
        temperature.setText(info.getTemp());

        TextView humidity = (TextView) findViewById(R.id.textViewHumidity);
        humidity.setText(info.getHumidity());

        TextView description = (TextView) findViewById(R.id.textViewDescription);
        description.setText(info.getDescription());
    }

}
