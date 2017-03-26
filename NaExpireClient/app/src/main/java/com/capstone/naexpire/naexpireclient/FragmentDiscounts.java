package com.capstone.naexpire.naexpireclient;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class FragmentDiscounts extends Fragment {

    ListAdapterDiscounts adapter;
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> price = new ArrayList<String>();
    ArrayList<String> description = new ArrayList<String>();
    ArrayList<String> restname = new ArrayList<String>();
    ArrayList<String> distance = new ArrayList<String>();
    ArrayList<String> quantity = new ArrayList<String>();


    public FragmentDiscounts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_discounts, container, false);

        FragmentDiscounts.this.getActivity().setTitle("Discounts"); //set activity title

        //test data
        name.add("Beef Taco");
        name.add("Chicken Taco");
        name.add("Shrimp Taco");
        restname.add("Chicken on a Stick");
        restname.add("Raising Canes");
        restname.add("Fiesta Burrito");
        price.add("$1.23");
        price.add("$2.34");
        price.add("$3.45");
        description.add("Taco with beef");
        description.add("Taco with chicken");
        description.add("Taco with shrimp");
        distance.add("7.2 miles");
        distance.add("3.5 miles");
        distance.add("8.1 miles");
        quantity.add("2");
        quantity.add("5");
        quantity.add("7");

        adapter = new ListAdapterDiscounts(FragmentDiscounts.this.getContext(), name, restname, price);
        ListView listview = (ListView) view.findViewById(R.id.lstDiscounts);
        listview.setAdapter(adapter);

        //spinner to select filter method for menu items
        Spinner spinner = (Spinner) view.findViewById(R.id.spnFilter);
        ArrayAdapter<CharSequence> spAdapter = ArrayAdapter.createFromResource(
                FragmentDiscounts.this.getContext(),
                R.array.filter_array, android.R.layout.simple_spinner_item);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id){
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(FragmentDiscounts.this.getContext());
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_discount_info, null);
                final TextView itemName = (TextView) dialogView.findViewById(R.id.lblInfoItemName);
                final TextView restName = (TextView) dialogView.findViewById(R.id.lblInfoRestName);
                final TextView itemPrice = (TextView) dialogView.findViewById(R.id.lblInfoPrice);
                final TextView itemDesc = (TextView) dialogView.findViewById(R.id.lblInfoDescription);
                final TextView restDist = (TextView) dialogView.findViewById(R.id.lblInfoDistance);
                Button cart = (Button) dialogView.findViewById(R.id.btnInfoCart);

                itemName.setText(name.get(position));
                itemPrice.setText(price.get(position));
                restName.setText(restname.get(position));
                itemDesc.setText(description.get(position));
                restDist.setText(distance.get(position));
                final int num = Integer.parseInt(quantity.get(position));

                String[] n = new String[num];
                for(int i = 0; i < num; i++){
                    n[i] = ""+(i+1);
                }

                final Spinner mspin=(Spinner) dialogView.findViewById(R.id.spnInfoQuantity);
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(FragmentDiscounts.this.getContext(),android.R.layout.simple_spinner_item, n);
                mspin.setAdapter(adapter);

                dialogBuilder.setView(dialogView);
                final AlertDialog dialog = dialogBuilder.create();
                dialog.show();

                cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int amount = Integer.parseInt(mspin.getSelectedItem().toString());
                        int newNum = num - amount;
                        quantity.set(position, ""+newNum);
                        if(newNum == 0){
                            Toast.makeText(FragmentDiscounts.this.getContext(),"removing",Toast.LENGTH_SHORT).show();
                            name.remove(position);
                            price.remove(position);
                            description.remove(position);
                            restname.remove(position);
                            distance.remove(position);
                            quantity.remove(position);
                            adapter.notifyDataSetChanged();
                            //doesn't get updated correctly...
                        }
                        dialog.dismiss();
                    }
                });
            }
        });

        return view;
    }
}
