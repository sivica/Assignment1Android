package com.stojanoski.ivica.assignment1android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Set;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mCitiesRecyclerView;
    private TextView mEmptyView;
    private App mApp;
    private CityAdapter mCityAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ItemTouchHelper.SimpleCallback mItemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                              RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            removeCity(viewHolder);
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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mCitiesRecyclerView.setLayoutManager(layoutManager);

        mCityAdapter = new CityAdapter(MainActivity.this);
        mCitiesRecyclerView.setAdapter(mCityAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mCitiesRecyclerView);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCities();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshCities();
    }

    private void refreshCities() {
        OpenWeatherService openWeatherApi = mApp.getOpenWeatherApi();
        String commaSeparatedIds = CityManager.getCommaSeparatedIds(this);

        openWeatherApi.groupWeatherByIds(commaSeparatedIds, OpenWeatherService.UNITS,
                OpenWeatherService.APPID, new Callback<GroupWeather>() {
                    @Override
                    public void success(GroupWeather groupWeather, Response response) {
                        mCityAdapter.addItems(groupWeather.getList());
                        checkIfEmpty();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Snackbar.make(mCitiesRecyclerView, error.getMessage(),
                                Snackbar.LENGTH_LONG).show();
                        checkIfEmpty();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    private void checkIfEmpty() {
        if (mCityAdapter.getItemCount() == 0) {
            mSwipeRefreshLayout.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
        }
    }

    private void removeCity(final RecyclerView.ViewHolder viewHolder) {
        final int position = viewHolder.getAdapterPosition();
        final String id = String.valueOf(viewHolder.itemView.getId());
        mCityAdapter.remove(position);

        Snackbar snackbar = Snackbar
                .make(mCitiesRecyclerView, getString(R.string.city_deleted), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCityAdapter.undoRemove(position);
                        checkIfEmpty();
                    }
                });
        snackbar.show();

        snackbar.setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                CityManager.removeCity(id, MainActivity.this);
            }
        });

        checkIfEmpty();
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
