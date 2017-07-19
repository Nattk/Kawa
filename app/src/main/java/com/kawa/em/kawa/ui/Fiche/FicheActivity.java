package com.kawa.em.kawa.ui.Fiche;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kawa.em.kawa.R;
import com.kawa.em.kawa.models.Cafes.Cafe;
import com.kawa.em.kawa.ui.Favoris.FavorisActivity;
import com.kawa.em.kawa.utils.FastDialog;
import com.kawa.em.kawa.utils.Preference;


public class FicheActivity extends AppCompatActivity {

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

        nomCafe = (TextView) findViewById(R.id.nomCafe);
        adresse = (TextView) findViewById(R.id.Adresse);
        codePostal = (TextView) findViewById(R.id.codePostal);
        PrixComptoire = (TextView) findViewById(R.id.PrixComptoire);
        PrixSalle = (TextView) findViewById(R.id.PrixSalle);
        PrixTerasse = (TextView) findViewById(R.id.PrixTerasse);

        nomCafe.setText(cafesData.nom_du_cafe);
        adresse.setText(cafesData.adresse);
        codePostal.setText(cafesData.arrondissement);
        PrixComptoire.setText("Prix comptoir : "+cafesData.prix_comptoir+" €");
        if( cafesData.prix_salle.indexOf("-") != -1 ){
            PrixSalle.setText("Ne propose pas de café en salle à 1€");
        }
        else{
            PrixSalle.setText("Prix salle : "+cafesData.prix_salle+" €");
        }

        if( cafesData.prix_terasse.indexOf("-") != -1 ){
            PrixTerasse.setText("Ne propose pas de café en terasse à 1€");
        }
        else{
            PrixTerasse.setText("Prix terasse : "+cafesData.prix_terasse+" €");
        }
    }

    public void addToFavorite(View view) {

        if(Preference.addFavorite(FicheActivity.this,cafesData)==true){

           Intent intentFiche = new Intent(FicheActivity.this, FavorisActivity.class);
           startActivity(intentFiche);
        }

        else{

            FastDialog.showDialog(FicheActivity.this, FastDialog.SIMPLE_DIALOG, "Ce café est déjà dans votre liste de favoris");
        }
    }
    public void sendMail(View view) {

        Intent intentEmail = new Intent(Intent.ACTION_SEND);

        intentEmail.setType("message/rfc822");
        intentEmail.putExtra(Intent.EXTRA_SUBJECT, "Un café dans paris à 1€ !");
        intentEmail.putExtra(Intent.EXTRA_TEXT, "Nom du café : "+nomCafe.getText().toString()+" \nAdresse : "+adresse.getText().toString()+" "+codePostal.getText().toString());

        startActivity(Intent.createChooser(intentEmail, "Envoyer un e-mail :"));
    }

    public void back(View view) {
        FicheActivity.this.finish();
    }

    public void favorite(View view) {
        Preference.setFavorite(FicheActivity.this);
        Intent intentFavoris = new Intent(FicheActivity.this, FavorisActivity.class);
        startActivity(intentFavoris);

    }
}
