package com.kawa.em.kawa.ui.ListeCafe;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kawa.em.kawa.R;
import com.kawa.em.kawa.models.Cafes.Cafes;

import java.util.List;

/**
 * Created by Armand on 18/07/2017.
 */

public class ListAdapter extends ArrayAdapter<Cafes> {


    private LayoutInflater inflater;
    private int resId; // R.layout.item_restaurant

    // paramètres : this, R.layout.item_restaurant, restaurantsList
    public ListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Cafes> objects) {
        super(context, resource, objects);

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
            viewHolder.textViewName = (TextView) convertView.findViewById(R.id.textViewName);
            viewHolder.textViewPostalCode = (TextView) convertView.findViewById(R.id.textViewPostalCode);
            viewHolder.textViewDistance = (TextView) convertView.findViewById(R.id.textViewDistance);

            convertView.setTag(viewHolder);
            // enregistre le ViewHolder qui contient le titre et la catégorie
        } else {
            viewHolder = (ViewHolder) convertView.getTag(); // récupération des TextView
        }

        // affichage des données
        Cafes item = getItem(position);

        viewHolder.textViewName.setText(item.getName());
        viewHolder.textViewPostalCode.setText(item.getPostalCode());
        viewHolder.textViewDistance.setText(item.getDistance()+" m");

        return convertView;
    }

    class ViewHolder {
        TextView textViewName;
        TextView textViewPostalCode;
        TextView textViewDistance;
    }
}
