package com.kawa.em.kawa.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kawa.em.kawa.models.Cafes.Cafe;

import java.lang.reflect.Array;
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

    private static List<Cafe> favoris = new ArrayList<>();
    private static String json = null;
    private static String TAG = "Preference";
    private static final String PREF_WELCOME = "welcome";
    private static final String PREF_LIST = "list";


    private static SharedPreferences getPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void addFavorite(Context context, Cafe cafe){

        setFavorite(context);
        Gson gson = new Gson();
        favoris.add(cafe);

        Type listType = new TypeToken<ArrayList<Cafe>>() {}.getType();
        String jsonList = gson.toJson(favoris, listType);
        json = jsonList;
        Log.e(TAG,"JsonString Add : "+json);
        getPreference(context)
                .edit()
                .putString(PREF_LIST, jsonList)
                .commit();

    }

    public static void setFavorite(Context context){
        Gson gson = new Gson();
        Type listTypeCafe = new TypeToken<ArrayList<Cafe>>(){}.getType();
        favoris = gson.fromJson(getPreference(context).getString(PREF_LIST, null), listTypeCafe);
        if(favoris == null){
            favoris = new ArrayList<>();
        }
    }

    public static void deleteFavorite(int i,Context context){

        favoris.remove(i);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Cafe>>() {}.getType();
        String jsonList = gson.toJson(favoris, listType);
        getPreference(context)
                .edit()
                .putString(PREF_LIST, jsonList)
                .commit();
    }

    public static List<Cafe> getFavorites(Context context){

        return favoris;
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
