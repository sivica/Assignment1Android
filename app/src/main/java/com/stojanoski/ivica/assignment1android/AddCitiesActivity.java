package com.stojanoski.ivica.assignment1android;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import java.util.HashSet;
import java.util.Set;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AddCitiesActivity extends AppCompatActivity {

    private EditText mEditTextCityName;
    private App mApp;
    private OpenWeatherService mOpenWeatherApi;
    private TextInputLayout mTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cities);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCityByName(mEditTextCityName.getText().toString());
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEditTextCityName = (EditText) findViewById(R.id.editTextCityName);
        mTextInputLayout = (TextInputLayout) findViewById(R.id.cityNameWrapper);
    }

    private void getCityByName(String cityName) {
        if (cityName.equals("")) {
            mTextInputLayout.setError(getString(R.string.enter_city));
            return;
        }
        mApp = ((App)getApplication());
        mOpenWeatherApi = mApp.getOpenWeatherApi();


        mOpenWeatherApi.currentWeatherByCityName(cityName, OpenWeatherService.UNITS,
                OpenWeatherService.APPID, new Callback<GroupWeather.CityWeather>() {
            @Override
            public void success(GroupWeather.CityWeather cityWeather, Response response) {
                if (cityWeather.getId() == null) {
                    mTextInputLayout.setError(getString(R.string.not_found));
                } else {
                    CityManager.addCity(cityWeather.getId(), AddCitiesActivity.this);
                    finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                mTextInputLayout.setError(error.getMessage());
            }
        });
    }
}
