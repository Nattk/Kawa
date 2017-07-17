package com.kawa.em.kawa.utils;

/**
 * Created by Nattan on 12/07/2017.
 */

public class Constant {

    public static final long SPLASH_TIME = 2000;
    public static final String URL_BASE = "https://opendata.paris.fr/api/records/1.0/search/";
    public static final String URL_ALL =  URL_BASE + "?dataset=liste-des-cafes-a-un-euro&rows=-1";
    public static final String URL_LIST =  URL_BASE + "?dataset=liste-des-cafes-a-un-euro&rows=-1&geofilter.distance=%s,%s,%s";

    public static final String BROADCAST_LATLNG = "com.kawa.em.kawa.latlng";
    public static final String INTENT_LNG = "lng";
    public static final String INTENT_LAT = "lat";
    public static final double MAP_DEFAULT_LAT = 42;
    public static final double MAP_DEFAULT_LNG = 2;
}
