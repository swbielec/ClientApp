package com.capstone.naexpire.naexpireclient;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class FragmentPreferences extends Fragment {


    public FragmentPreferences() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_preferences, container, false);

        FragmentPreferences.this.getActivity().setTitle("Preferences"); //set title

        EditText username = (EditText) view.findViewById(R.id.txtPrefUsername);
        EditText email = (EditText) view.findViewById(R.id.txtPrefEmail);
        EditText phone = (EditText) view.findViewById(R.id.txtPrefPhone);
        Button foods = (Button) view.findViewById(R.id.btnPrefFoods);
        Button save = (Button) view.findViewById(R.id.btnPrefSave);

        username.setText("BenL");
        email.setText("linus@gmail.com");
        phone.setText("4808377049");

        foods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentEditFoodTypes fragmentEditFoodTypes = new FragmentEditFoodTypes();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, fragmentEditFoodTypes).commit();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FragmentPreferences.this.getContext(), "Changes Saved", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
