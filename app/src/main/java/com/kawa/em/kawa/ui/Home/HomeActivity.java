package com.kawa.em.kawa.ui.Home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;

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
import com.kawa.em.kawa.ui.ListeCafe.ListFragment;
import com.kawa.em.kawa.ui.map.MapFragment;
import com.kawa.em.kawa.utils.Constant;
import com.kawa.em.kawa.utils.Network;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private String TAG = "Home";
    private List<Cafe> cafesList = new ArrayList<>();


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        if(Network.isNetworkAvailable(HomeActivity.this)){

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = String.format(Constant.URL_BASE);

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override

                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            Log.e(TAG,"onResponse"+response);
                            Gson gson = new Gson();
                            Records records =  gson.fromJson(response, Records.class);

                            Log.e(TAG,"Records"+records.records);

                           if(records.records != null){

                               Log.e(TAG,"GetRecords :"+ records.getRecords());
                               cafesList.add(records.records[0].fields);

                            }

                            else{
                                Log.e(TAG,"onError : Rien dans records");

                            }

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG,"onError" + error);
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
            switch (position){
                default:

                    return new MapFragment();
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
