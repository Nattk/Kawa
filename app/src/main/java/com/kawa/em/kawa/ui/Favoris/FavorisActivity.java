package com.kawa.em.kawa.ui.Favoris;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.kawa.em.kawa.R;
import com.kawa.em.kawa.models.Cafes.Cafe;
import com.kawa.em.kawa.utils.Preference;

import java.util.ArrayList;
import java.util.List;

public class FavorisActivity extends AppCompatActivity {

    private ListView listViewData;
    private TextView nomCafe;
    private TextView codePostal;

    private List<Cafe> cafeList = new ArrayList<>();
    private String TAG = "Favoris";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);

        cafeList = Preference.getFavorites(FavorisActivity.this);
        nomCafe = (TextView) findViewById(R.id.nomCafe);
        codePostal = (TextView) findViewById(R.id.codePostal);
        listViewData = (ListView) findViewById(R.id.listViewData);

        Log.e(TAG,"Favoris List "+cafeList);

        listViewData.setAdapter(new FavorisAdapter(FavorisActivity.this,
                R.layout.item_cafe, cafeList));
        }
}
