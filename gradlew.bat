package com.kifoyi.nattan.notedefrais;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.kifoyi.nattan.notedefrais.model.Companies;
import com.kifoyi.nattan.notedefrais.model.Company;
import com.kifoyi.nattan.notedefrais.model.User;
import com.kifoyi.nattan.notedefrais.utils.Network;
import com.kifoyi.nattan.notedefrais.utils.Preferences;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CompanyActivity extends AppCompatActivity {

    private ListView companyListView ;
    private List<Companies> companyList = new ArrayList<>(); // initialisation de la liste
    private ArrayAdapter<Companies> adapter;
    private User  myUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        myUser = Preferences.getUser(CompanyActivity.this);
        companyListView = (ListView) findViewById(R.id.list);
        adapter = new ArrayAdapter<Companies>(CompanyActivity.this,
                android.R.layout.simple_list_item_1,
                companyList);
        companyListView.setAdapter(adapter);

        String session = "http://formation-api-notefrais.client.vivaneo.fr/v1/companies?session="+myUser.session;

        if(Network.isNetworkAvailable(CompanyActivity.this)) {

            RequestQueue queue = Volley.newRequestQueue(CompanyActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.GET,session ,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            Log.d("Response", response);
                            Gson gson = new Gson();
                            Company companies = gson.fromJson(response, C