package com.stojanoski.ivica.assignment1android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mCitiesRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mEmptyView;
    private ArrayList<String> mCities;
    private OpenWeatherService mOpenWeatherApi;
    private App mApp;
    private CityAdapter mCityAdapter;

    private ItemTouchHelper.SimpleCallback mItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                              RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            mCityAdapter.remove(viewHolder.getAdapterPosition());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddCitiesActivity.class));
            }
        });

        mApp = ((App)getApplication());
        mCitiesRecyclerView = (RecyclerView) findViewById(R.id.cities_recycler_view);
        mEmptyView = (TextView) findViewById(R.id.empty_view);

        mLayoutManager = new LinearLayoutManager(this);
        mCitiesRecyclerView.setLayoutManager(mLayoutManager);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mCitiesRecyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getGroupWeather();
    }

    private void getGroupWeather() {
        mOpenWeatherApi = mApp.getOpenWeatherApi();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String ids = prefs.getString("city_ids", "");

        mOpenWeatherApi.groupWeatherByIds(ids, OpenWeatherService.UNITS,
                OpenWeatherService.APPID, new Callback<GroupWeather>() {
            @Override
            public void success(GroupWeather groupWeather, Response response) {
                Log.d(TAG, groupWeather.toString());

                mCityAdapter = new CityAdapter(MainActivity.this, groupWeather.getList());
                mCitiesRecyclerView.setAdapter(mCityAdapter);
                checkIfEmpty();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.getMessage());

                mCityAdapter = new CityAdapter(mApp.getApplicationContext(),
                        new ArrayList<GroupWeather.CityWeather>());
                mCitiesRecyclerView.setAdapter(mCityAdapter);
                checkIfEmpty();
            }
        });
    }

    private void checkIfEmpty() {
        if (mCityAdapter.getItemCount() == 0) {
            mCitiesRecyclerView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mCitiesRecyclerView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
