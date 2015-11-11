package com.stojanoski.ivica.assignment1android;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ivica on 11/11/2015.
 */
public class CityManager {

    private static final String CITIES_PREF = "CITIES_PREF";
    private static final String CITY_IDS = "CITY_IDS";

    public static String getCommaSeparatedIds(Context context) {
        SharedPreferences pref = context.getSharedPreferences(CITIES_PREF, Context.MODE_PRIVATE);
        Set<String> cityIds = pref.getStringSet(CITY_IDS, new HashSet<String>());

        String commaSeparatedIds = "";
        for (String id:cityIds) {
            commaSeparatedIds = commaSeparatedIds + id + ",";
        }

        return commaSeparatedIds;
    }

    public static void addCity(String id, Context context) {
        SharedPreferences pref = context.getSharedPreferences(CITIES_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        Set<String> cityIds = pref.getStringSet(CITY_IDS, new HashSet<String>());
        cityIds.add(id);
        editor.putStringSet(CITY_IDS, cityIds);
        editor.apply();
    }


    public static void removeCity(String id, Context context) {
        SharedPreferences pref = context.getSharedPreferences(CITIES_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        Set<String> cityIds = pref.getStringSet(CITY_IDS, new HashSet<String>());
        cityIds.remove(id);
        editor.putStringSet(CITY_IDS, cityIds);
        editor.apply();
    }
}
