package com.kawa.em.kawa.ui.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kawa.em.kawa.R;
import com.kawa.em.kawa.models.Cafes.Cafes;
import com.kawa.em.kawa.ui.ListeCafe.ListFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private List<Cafes> cafes = new ArrayList<>();
    private String TAG = "FragmentMap";

    // TODO: Rename and change types of parameters
    private Serializable mParam1;

    private GoogleMap mMap;
    private Fragment map;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return  inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        LatLng paris = new LatLng(48.866667, 2.333333);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(paris));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(paris, 11));

        if (getArguments() != null) {
            mParam1 = getArguments().getSerializable("listCafes");

            cafes = (List<Cafes>) mParam1;

            if (cafes != null) {
                for (int i = 0; i < cafes.size(); i++) {

                    if (cafes.get(i).fields.geoloc != null) {
                        LatLng latLng = new LatLng(cafes.get(i).fields.geoloc.get(0), cafes.get(i).fields.geoloc.get(1));
                        mMap.addMarker(new MarkerOptions().position(latLng).title(cafes.get(i).fields.nom_du_cafe));
                    }

                }
            }

        }

    }

    public static MapFragment newInstance(List<Cafes> param1) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putSerializable("listCafes", (Serializable) param1);
        fragment.setArguments(args);
        return fragment;
    }
}
