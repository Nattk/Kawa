package com.kawa.em.kawa.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kawa.em.kawa.R;
import com.kawa.em.kawa.models.Cafes.Cafes;
import com.kawa.em.kawa.ui.Fiche.FicheActivity;
import com.kawa.em.kawa.ui.GPSTracker.GPSTracker;
import com.kawa.em.kawa.ui.Home.HomeActivity;
import com.kawa.em.kawa.ui.ListeCafe.ListFragment;
import com.kawa.em.kawa.utils.Constant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private String TAG = "FragmentMap";

    // TODO: Rename and change types of parameters
    private Serializable mParam1;

    private static GoogleMap mMap;
    private Fragment map;
    private LatLng oldLatLng;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getSerializable("allCafes");


        }
    }

    public static void notifyData(Context c, List<Cafes> cafes) {
        if(mMap != null) {
            getData(c, cafes);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.e(TAG, "on resume map");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setMyLocationEnabled();
            }
        }
    }

    private void setMyLocationEnabled() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            Log.e(TAG, "error Permission");

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
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

        GPSTracker gps = new GPSTracker(getContext());
        if(gps.canGetLocation()) { // gps enabled} // return boolean true/false

        }

        mMap = googleMap;
        LatLng paris = new LatLng(gps.getLatitude(), gps.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(paris));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(paris, 14));

        setMyLocationEnabled();

        notifyData(getContext(), (List<Cafes>) mParam1);


        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {


                if(oldLatLng != null) {
                    float[] results = new float[1];
                    Location.distanceBetween(oldLatLng.latitude, oldLatLng.longitude,
                            mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude, results);


                    if(results[0] > 1000) {
                        oldLatLng = mMap.getCameraPosition().target;

                        // sendBroacast
                        Log.e(TAG, "result: "+results[0]);

                        getContext().sendBroadcast(new Intent(Constant.BROADCAST_LATLNG)
                                .putExtra(Constant.INTENT_LAT, oldLatLng.latitude)
                                .putExtra(Constant.INTENT_LNG, oldLatLng.longitude));

                    }

                } else {
                    oldLatLng = mMap.getCameraPosition().target;

                }



                //Log.e(TAG, "setOnCameraMoveListener: "+mMap.getCameraPosition().target.latitude+", "+mMap.getCameraPosition().target.longitude);
            }
        });
    }

    /** Called when the user clicks a marker. */
    public boolean onMarkerClick(final Marker marker) {

        Log.e(TAG, "onClick : " + marker.getTag());
        Intent intentFiche = new Intent(getContext(), FicheActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Cafes", (Serializable) marker.getTag());
        intentFiche.putExtras(bundle);
        startActivity(intentFiche);


        return false;
    }

    public static MapFragment newInstance(List<Cafes> param1) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putSerializable("allCafes", (Serializable) param1);
        fragment.setArguments(args);
        return fragment;
    }

    public static void getData(Context c, List<Cafes> cafes) {
        mMap.clear();

        if (cafes != null) {
            for (int i = 0; i < cafes.size(); i++) {

                if (cafes.get(i).fields.geoloc != null) {
                    LatLng latLng = new LatLng(cafes.get(i).fields.geoloc.get(0), cafes.get(i).fields.geoloc.get(1));
                    Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(cafes.get(i).fields.nom_du_cafe).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker)));
                    // Add data object
                    marker.setTag(cafes.get(i).fields);

                }

            }
        }
    }
}
