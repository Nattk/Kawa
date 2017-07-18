package com.kawa.em.kawa.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Nattan on 15/07/2017.
 */

public class Preference {

    private static final String PREF_WELCOME = "welcome";

    private static SharedPreferences getPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
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
