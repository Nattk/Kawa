package com.kawa.em.kawa.ui.Home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.kawa.em.kawa.R;
import com.kawa.em.kawa.models.Cafes.Cafe;
import com.kawa.em.kawa.models.Cafes.Cafes;
import com.kawa.em.kawa.models.Cafes.Records;
import com.kawa.em.kawa.ui.Favoris.FavorisActivity;
import com.kawa.em.kawa.ui.GPSTracker.GPSTracker;
import com.kawa.em.kawa.ui.ListeCafe.ListFragment;
import com.kawa.em.kawa.ui.map.MapFragment;
import com.kawa.em.kawa.utils.Constant;
import com.kawa.em.kawa.utils.FastDialog;
import com.kawa.em.kawa.utils.Network;
import com.kawa.em.kawa.utils.Preference;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class HomeActivity extends AppCompatActivity {

    private String TAG = "Home";
    private List<Cafes> cafesList = new ArrayList<>();

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    public boolean isLocationUpdated;

    private ReceiverLatLng receiverLatLng;
    private GPSTracker gps;

    public void favorite(View view) {
        Preference.setFavorite(HomeActivity.this);
        Intent intentFavoris = new Intent(HomeActivity.this, FavorisActivity.class);
        startActivity(intentFavoris);


    }

    private class ReceiverLatLng extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if(isLocationUpdated) {
                getData(intent.getDoubleExtra(Constant.INTENT_LAT, Constant.MAP_DEFAULT_LAT), intent.getDoubleExtra(Constant.INTENT_LNG, Constant.MAP_DEFAULT_LNG));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (Preference.getWelcome(HomeActivity.this) == null) {
            FastDialog.showDialog(HomeActivity.this, FastDialog.SIMPLE_DIALOG, "Bienvenu sur Kawa ! Retrouvez tout les cafés de paris à 1€ !");
            Preference.setWelcome(HomeActivity.this, "ok");
        }

        // receiver map latlng
        receiverLatLng = new ReceiverLatLng();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    protected void onResume() {
        super.onResume();

        gps = new GPSTracker(HomeActivity.this);

        if(gps.canGetLocation()) {
            isLocationUpdated = true;

            if(gps.getLocation() != null) {
                getData(gps.getLocation().getLatitude(), gps.getLocation().getLongitude());
            }
        } else {
            FastDialog.showDialog(HomeActivity.this, FastDialog.SIMPLE_DIALOG, "Activer le GPS", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO : rediriger vers activation gps
                    gps.showSettingsAlert();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }

        registerReceiver(receiverLatLng, new IntentFilter(Constant.BROADCAST_LATLNG));
    }

    @Override
    protected void onPause() {
        super.onPause();


        unregisterReceiver(receiverLatLng);
    }

    public void getData(double lat, double lon) {
        if (Network.isNetworkAvailable(HomeActivity.this)) {

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);

            String url = String.format(Constant.URL_LIST, lat, lon, String.valueOf(1500));

            Log.e(TAG, "url: "+url);

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            //Log.e(TAG, "onResponse" + response);
                            Gson gson = new Gson();
                            Records records = gson.fromJson(response, Records.class);

                            cafesList.clear();
                            if (records.records != null) {
                                cafesList.addAll(records.records);
                            } else {
                                //Log.e(TAG, "onError : Rien dans records");

                            }

                            MapFragment.notifyData(HomeActivity.this, cafesList);
                            ListFragment.notifyData(HomeActivity.this, cafesList);


                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "onError" + error);
                }

            });
    // Add the request to the RequestQueue.
            queue.add(stringRequest);

        } else {

            // FastDialog.showDialog(HomeActivity.this,FastDialog.SIMPLE_DIALOG, getString(R.string.dialog_network_error));
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                default:
                    return MapFragment.newInstance(cafesList);
                case 1:
                    return ListFragment.newInstance(cafesList);
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Carte";
                case 1:
                    return "Liste";
            }
            return null;
        }
    }
}
