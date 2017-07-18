package com.kawa.em.kawa.ui.ListeCafe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kawa.em.kawa.R;
import com.kawa.em.kawa.models.Cafes.Cafes;
import com.kawa.em.kawa.ui.Fiche.FicheActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment {

    private ListView listViewCafes;
    private static List<Cafes> cafes = new ArrayList<>();
    private static ArrayAdapter<Cafes> adapter;

    private String TAG = "FragmentList";

    // TODO: Rename and change types of parameters
    private Serializable mParam1;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(List<Cafes> param1) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putSerializable("allCafes", (Serializable) param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    public static void notifyData(Context c, List<Cafes> cafesList) {
        cafes.addAll(cafesList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getSerializable("allCafes");
            Log.e(TAG, "Cafes : "+mParam1);

            cafes = (List<Cafes>) mParam1;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listViewCafes = (ListView) view.findViewById(R.id.listViewCafes);

        // adapter
        adapter = new ListAdapter(getContext(), R.layout.item_list, cafes);
        listViewCafes.setAdapter(adapter);

        listViewCafes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intentFiche = new Intent(getContext(), FicheActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Cafes", (Serializable) cafes.get(position).fields);
                intentFiche.putExtras(bundle);
                startActivity(intentFiche);

            }
        });

        return view;

    }


}
