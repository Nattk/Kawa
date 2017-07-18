package com.kawa.em.kawa.ui.Favoris;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kawa.em.kawa.R;
import com.kawa.em.kawa.models.Cafes.Cafe;

import java.util.List;

/**
 * Created by Nattan on 18/07/2017.
 */

public class FavorisAdapter extends ArrayAdapter<Cafe> {


    private LayoutInflater inflater;
    private int resId; // R.layout.item_restaurant

    public FavorisAdapter(@NonNull Context context, @LayoutRes int resource, List<Cafe> listViewData) {
        super(context, resource);

        resId = resource; // récupération du chemin (int resource) vers R.layout.item_restaurant
        inflater = LayoutInflater.from(context); // init de l'Inflater
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder; // déclaration

        // remplacement de la vue par notre layout item_restaurant
        if(convertView == null) {
            convertView = inflater.inflate(resId, null);
            //convertView = inflater.inflate(R.layout.item_restaurant, null);

            viewHolder = new ViewHolder(); // instance

            // récupération des vues
            viewHolder.nomCafe = (TextView) convertView.findViewById(R.id.nomCafe);
            viewHolder.codePostal = (TextView) convertView.findViewById(R.id.codePostal);

            convertView.setTag(viewHolder);
            // enregistre le ViewHolder qui contient le titre et la catégorie
        } else {
            viewHolder = (ViewHolder) convertView.getTag(); // récupération des TextView
        }

        // affichage des données
        Cafe item = getItem(position);

        viewHolder.nomCafe.setText(item.nom_du_cafe);
        viewHolder.codePostal.setText(item.arrondissement);

        return convertView;
    }

    class ViewHolder {
        TextView nomCafe;
        TextView codePostal;
    }
}
