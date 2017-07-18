package com.kawa.em.kawa.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kawa.em.kawa.models.Cafes.Cafe;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Nattan on 15/07/2017.
 */

public class Preference {

    private static final  List<Cafe> favoris = new ArrayList<>();
    private static String json = null;
    private static String TAG = "Preference";
    private static final String PREF_WELCOME = "welcome";



    private static SharedPreferences getPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void addFavorite(Context context, Cafe cafe){

        favoris.add(cafe);

        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Cafe>>() {}.getType();
        String jsonList = gson.toJson(favoris, listType);
        json = jsonList;

        getPreference(context)
                .edit()
                .putString("List", jsonList)
                .commit();
    }

    public void deleteFavorite(int i){

        favoris.remove(i);
    }

    public static List<Cafe> getFavorites(Context context){

        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Cafe>>(){}.getType();
        List<Cafe> cafePreference = gson.fromJson(getPreference(context).getString("List",null), listType);

        return cafePreference;
    }


    public static void setWelcome(Context context, String welcome){
        getPreference(context)
                .edit()
                .putString(PREF_WELCOME, welcome)
                .commit();
    }

    public static String getWelcome(Context context){
        return getPreference(context).getString(PREF_WELCOME, null);
    }

}
