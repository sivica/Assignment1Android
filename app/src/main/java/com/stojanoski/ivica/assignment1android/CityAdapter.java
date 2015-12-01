package com.stojanoski.ivica.assignment1android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by sivic on 11/9/2015.
 */
public class CityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater mInflater;
    private List<GroupWeather.CityWeather> mItems;
    private Context mContext;
    private GroupWeather.CityWeather mDeletedCity;

    public CityAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mItems = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CityHolder(mInflater.inflate(R.layout.city_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final GroupWeather.CityWeather item = getItem(position);
        CityHolder cityHolder = (CityHolder) holder;
        cityHolder.mCityName.setText(item.getName());
        cityHolder.mTemperature.setText(item.getTemp());
        cityHolder.itemView.setId(Integer.parseInt(item.getId()));

        cityHolder.itemView.setOnClickListener(new View.OnClickListener() {
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
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mDeletedCity = mItems.get(position);
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public void undoRemove(int position) {
        mItems.add(position, mDeletedCity);
        notifyDataSetChanged();
    }

    class CityHolder extends RecyclerView.ViewHolder {
        public TextView mCityName;
        public TextView mTemperature;

        public CityHolder(View itemView) {
            super(itemView);
            mCityName = (TextView) itemView.findViewById(R.id.textViewListCityName);
            mTemperature = (TextView) itemView.findViewById(R.id.textViewListTemperature);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu popup = new PopupMenu(v.getContext(), v);
                    popup.inflate(R.menu.context_recycler_view_menu);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_one:
                                    Toast.makeText(mContext,"Action one selected", Toast.LENGTH_LONG).show();
                                    return true;
                                case R.id.action_two:
                                    Toast.makeText(mContext,"Action two selected", Toast.LENGTH_LONG).show();
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popup.show();
                    return false;
                }
            });
        }
    }
}
