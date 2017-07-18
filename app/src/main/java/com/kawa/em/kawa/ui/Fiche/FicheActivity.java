package com.kawa.em.kawa.ui.Fiche;

import android.content.Intent;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kawa.em.kawa.R;
import com.kawa.em.kawa.models.Cafes.Cafe;
import com.kawa.em.kawa.models.Cafes.Cafes;
import com.kawa.em.kawa.ui.Favoris.FavorisActivity;
import com.kawa.em.kawa.utils.Preference;

import java.io.Serializable;

public class FicheActivity extends AppCompatActivity {

    private String TAG = "Home";
    private ImageButton imageButtonFavorite;
    private TextView nomCafe;
    private TextView adresse;
    private TextView codePostal;
    private TextView PrixComptoire;
    private TextView PrixSalle;
    private TextView PrixTerasse;
    private Cafe     cafesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche);

        cafesData = (Cafe) getIntent().getExtras().get("Cafes");

        imageButtonFavorite = (ImageButton) findViewById(R.id.imageButton_favorite);
        nomCafe = (TextView) findViewById(R.id.nomCafe);
        adresse = (TextView) findViewById(R.id.Adresse);
        codePostal = (TextView) findViewById(R.id.codePostal);
        PrixComptoire = (TextView) findViewById(R.id.PrixComptoire);
        PrixSalle = (TextView) findViewById(R.id.PrixSalle);
        PrixTerasse = (TextView) findViewById(R.id.PrixTerasse);

        nomCafe.setText(cafesData.nom_du_cafe);
        adresse.setText(cafesData.adresse);
        codePostal.setText(cafesData.arrondissement);
        PrixComptoire.setText("Prix comptoir : "+cafesData.prix_comptoir);
        if( cafesData.prix_salle.indexOf("-") != -1 ){
            PrixSalle.setText("Ce café dispose d'une salle");
        }
        else{
            PrixSalle.setText(cafesData.prix_salle);
        }

        if( cafesData.prix_terasse.indexOf("-") != -1 ){
            PrixTerasse.setText("Ce café dispose d'une Terrasse");
        }
        else{
            PrixTerasse.setText(cafesData.prix_salle);
        }

    }

    public void addToFavorite(View view) {

        Intent intentFiche = new Intent(FicheActivity.this, FavorisActivity.class);
        startActivity(intentFiche);
        Preference.addFavorite(FicheActivity.this,cafesData);

    }
}
