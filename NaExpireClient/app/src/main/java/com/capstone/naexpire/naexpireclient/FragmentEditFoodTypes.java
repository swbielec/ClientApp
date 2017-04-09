package com.capstone.naexpire.naexpireclient;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class FragmentEditFoodTypes extends Fragment {

    ListAdapterFoods adapter;
    ArrayList<String> foods = new ArrayList<String>();
    ArrayList<String> checked = new ArrayList<String>();

    public FragmentEditFoodTypes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_foods, container, false);

        //dummy data
        foods.add("Mexican");
        foods.add("Cajun");
        foods.add("Vietnamese");
        foods.add("Chinese");
        foods.add("Mediterranean");

        adapter = new ListAdapterFoods(FragmentEditFoodTypes.this.getContext(), foods, checked);
        final ListView listView = (ListView) view.findViewById(R.id.lstPrefFoods);
        listView.setAdapter(adapter);

        Button userprefs = (Button) view.findViewById(R.id.btnPrefUser);
        Button save = (Button) view.findViewById(R.id.btnPrefSaveFoods);

        userprefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentPreferences fragmentPreferences = new FragmentPreferences();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, fragmentPreferences).commit();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FragmentEditFoodTypes.this.getContext(), "Changes Saved", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
