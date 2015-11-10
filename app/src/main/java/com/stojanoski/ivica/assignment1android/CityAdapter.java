package com.stojanoski.ivica.assignment1android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by sivic on 11/9/2015.
 */
public class CityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater mInflater;
    private List<GroupWeather.CityWeather> mItems;
    private Context mContext;

    public CityAdapter(Context context, List<GroupWeather.CityWeather> items) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mItems = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CityHolder(mInflater.inflate(R.layout.city_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final GroupWeather.CityWeather item = getItem(position);
        ((CityHolder)holder).mCityName.setText(item.getName());
        ((CityHolder)holder).mTemperature.setText(item.getTemp());

        ((CityHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CityInfoActivity.class);
                intent.putExtra(CityInfoActivity.EXTRA_INFO, item.getName());
                mContext.startActivity(intent);
            }
        });
    }

    private GroupWeather.CityWeather getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addItems(List<GroupWeather.CityWeather> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    class CityHolder extends RecyclerView.ViewHolder {
        public TextView mCityName;
        public TextView mTemperature;

        public CityHolder(View itemView) {
            super(itemView);
            mCityName = (TextView) itemView.findViewById(R.id.textViewListCityName);
            mTemperature = (TextView) itemView.findViewById(R.id.textViewListTemperature);
        }
    }
}
