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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class FragmentDiscounts extends Fragment {

    ListAdapterDiscounts adapter;
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<Double> price = new ArrayList<Double>();
    ArrayList<String> description = new ArrayList<String>();
    ArrayList<String> restname = new ArrayList<String>();
    ArrayList<Double> distance = new ArrayList<Double>();
    ArrayList<Integer> quantity = new ArrayList<Integer>();
    ArrayList<String> image = new ArrayList<String>();


    public FragmentDiscounts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_discounts, container, false);

        FragmentDiscounts.this.getActivity().setTitle("Discounts"); //set activity title

        adapter = new ListAdapterDiscounts(FragmentDiscounts.this.getContext());
        ListView listview = (ListView) view.findViewById(R.id.lstDiscounts);
        listview.setAdapter(adapter);

        //spinner to select filter method for menu items
        Spinner spinner = (Spinner) view.findViewById(R.id.spnFilter);
        ArrayAdapter<CharSequence> spAdapter = ArrayAdapter.createFromResource(
                FragmentDiscounts.this.getContext(),
                R.array.filter_array, android.R.layout.simple_spinner_item);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spAdapter);

        //test data
        name.add("Beef Taco");
        name.add("Caesar Salad");
        name.add("Chicken Taco");
        name.add("Cheeseburger");
        name.add("Shrimp Taco");
        name.add("Spaghetti Carbonara");
        restname.add("Chicken on a Stick");
        restname.add("DJ's Bagels");
        restname.add("Raising Canes");
        restname.add("In n Out");
        restname.add("Fiesta Burrito");
        restname.add("Noodles");
        price.add(1.23);
        price.add(3.43);
        price.add(2.34);
        price.add(2.67);
        price.add(3.45);
        price.add(2.35);
        description.add("Taco with beef");
        description.add("Leafy greens");
        description.add("Taco with chicken");
        description.add("Burger with cheese");
        description.add("Taco with shrimp");
        description.add("Pasta with pasta sauce");
        distance.add(7.2);
        distance.add(12.5);
        distance.add(3.5);
        distance.add(1.2);
        distance.add(8.1);
        distance.add(4.2);
        quantity.add(2);
        quantity.add(1);
        quantity.add(5);
        quantity.add(3);
        quantity.add(7);
        quantity.add(3);
        image.add("android.resource://com.capstone.naexpire.naexpireclient/drawable/tacos");
        image.add("android.resource://com.capstone.naexpire.naexpireclient/drawable/salad");
        image.add("android.resource://com.capstone.naexpire.naexpireclient/drawable/tacos2");
        image.add("android.resource://com.capstone.naexpire.naexpireclient/drawable/burger");
        image.add("android.resource://com.capstone.naexpire.naexpireclient/drawable/shrimp");
        image.add("android.resource://com.capstone.naexpire.naexpireclient/drawable/carbonara");

        for(int i = 0; i < name.size(); i++){
            adapter.newItem(name.get(i),price.get(i),restname.get(i), image.get(i), description.get(i),
                    distance.get(i), quantity.get(i));
        }
        adapter.sortDiscounts(spinner.getSelectedItemPosition());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                adapter.sortDiscounts(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id){
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(FragmentDiscounts.this.getContext());
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_discount_info, null);
                final TextView itemName = (TextView) dialogView.findViewById(R.id.lblInfoItemName);
                final TextView restName = (TextView) dialogView.findViewById(R.id.lblInfoRestName);
                final TextView itemPrice = (TextView) dialogView.findViewById(R.id.lblInfoPrice);
                final TextView itemDesc = (TextView) dialogView.findViewById(R.id.lblInfoDescription);
                final TextView restDist = (TextView) dialogView.findViewById(R.id.lblInfoDistance);
                ImageView itemPic = (ImageView) dialogView.findViewById(R.id.imgInfoPicture);
                Button cart = (Button) dialogView.findViewById(R.id.btnInfoCart);

                itemName.setText(adapter.getName(position));
                itemPrice.setText("$"+adapter.getPrice(position));
                restName.setText(adapter.getRestaurant(position));
                itemDesc.setText(adapter.getDescription(position));
                restDist.setText(adapter.getDistance(position)+" miles");
                final int num = adapter.getQuantity(position);
                Glide.with(FragmentDiscounts.this.getContext()).load(adapter.getImage(position)).into(itemPic);

                String[] n = new String[num];
                for(int i = 0; i < num; i++){
                    n[i] = ""+(i+1);
                }

                final Spinner mspin=(Spinner) dialogView.findViewById(R.id.spnInfoQuantity);
                final ArrayAdapter<String> sAdapter = new ArrayAdapter<String>(FragmentDiscounts.this.getContext(),android.R.layout.simple_spinner_item, n);
                mspin.setAdapter(sAdapter);

                dialogBuilder.setView(dialogView);
                final AlertDialog dialog = dialogBuilder.create();
                dialog.show();

                cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int amount = Integer.parseInt(mspin.getSelectedItem().toString());
                        int newNum = num - amount;
                        adapter.setQuantity(position, newNum);
                        if(newNum == 0){
                            adapter.deleteItem(position);
                        }
                        dialog.dismiss();
                    }
                });
            }
        });

        return view;
    }
}
