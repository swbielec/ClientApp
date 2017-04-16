package com.capstone.naexpire.naexpireclient;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class FragmentDeals extends Fragment {
    DatabaseHelperDeals dbHelperDeals = null;
    DatabaseHelperCart dbHelperCart = null;

    ImageButton goToCart;

    ListAdapterDeals adapter;
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<Double> price = new ArrayList<Double>();
    ArrayList<String> description = new ArrayList<String>();
    ArrayList<String> restname = new ArrayList<String>();
    ArrayList<Double> distance = new ArrayList<Double>();
    ArrayList<Integer> quantity = new ArrayList<Integer>();
    ArrayList<String> image = new ArrayList<String>();
    ArrayList<String> cartNames = new ArrayList<String>();
    ArrayList<Integer> cartQuantities = new ArrayList<Integer>();


    public FragmentDeals() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_deals, container, false);

        dbHelperDeals = new DatabaseHelperDeals(getActivity().getApplicationContext());
        dbHelperCart = new DatabaseHelperCart(getActivity().getApplicationContext());

        FragmentDeals.this.getActivity().setTitle("Discounts"); //set activity title

        adapter = new ListAdapterDeals(FragmentDeals.this.getContext());
        ListView listview = (ListView) view.findViewById(R.id.lstDiscounts);
        listview.setAdapter(adapter);

        goToCart = (ImageButton) view.findViewById(R.id.imgbtnCart);

        //spinner to select filter method for menu items
        Spinner spinner = (Spinner) view.findViewById(R.id.spnFilter);
        ArrayAdapter<CharSequence> spAdapter = ArrayAdapter.createFromResource(
                FragmentDeals.this.getContext(),
                R.array.filter_array, android.R.layout.simple_spinner_item);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spAdapter);

        //get discounts from database & put them in arraylists

        //get if items are in cart to subtract form deals quantity totals
        SQLiteDatabase dbCart = dbHelperCart.getReadableDatabase();

        Cursor result = dbCart.rawQuery("SELECT name, quantity FROM cart", null);

        while(result.moveToNext()){
            cartNames.add(result.getString(0));
            cartQuantities.add(Integer.parseInt(result.getString(1)));
        }

        dbCart.close();
        result.close();

        //test data
        /*name.add("Beef Taco");
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
        restname.add("Noodles Inc");
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

        SQLiteDatabase db = dbHelperDeals.getWritableDatabase();
        for(int i = 0; i < name.size(); i++){
            int q = quantity.get(i);
            for(int j = 0; j < cartNames.size(); j++){
                if((name.get(i)).equals(cartNames.get(j)))
                    q -= cartQuantities.get(j);
            }
            ContentValues values = new ContentValues();

            values.put("id", "1");
            values.put("name", name.get(i));
            values.put("restaurant", restname.get(i));
            values.put("address", distance.get(i));
            values.put("description", description.get(i));
            values.put("price", price.get(i));
            values.put("image", image.get(i));
            values.put("quantity", q);
            db.insert("deals", null, values);

        }
        db.close();*/

        SQLiteDatabase dbDeals = dbHelperDeals.getReadableDatabase();

        Cursor dealsResult = dbDeals.rawQuery("SELECT name, restaurant, address, description," +
                "price, image, quantity FROM deals", null);

        while(dealsResult.moveToNext()){
            int q = Integer.parseInt(dealsResult.getString(6));
            for(int j = 0; j < cartNames.size(); j++){
                if((dealsResult.getString(0)).equals(cartNames.get(j)))
                    q -= cartQuantities.get(j);
            }
            if(q > 0)
                adapter.newItem(dealsResult.getString(0),Double.parseDouble(dealsResult.getString(4)),dealsResult.getString(1),
                        dealsResult.getString(5), dealsResult.getString(3), Double.parseDouble(dealsResult.getString(2)), q);
        }

        dbDeals.close();
        dealsResult.close();

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
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(FragmentDeals.this.getContext());
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_discount_info, null);
                final TextView itemName = (TextView) dialogView.findViewById(R.id.lblInfoItemName);
                final TextView restName = (TextView) dialogView.findViewById(R.id.lblInfoRestName);
                final TextView itemPrice = (TextView) dialogView.findViewById(R.id.lblInfoPrice);
                final TextView itemDesc = (TextView) dialogView.findViewById(R.id.lblInfoDescription);
                final TextView restDist = (TextView) dialogView.findViewById(R.id.lblInfoDistance);
                ImageView itemPic = (ImageView) dialogView.findViewById(R.id.imgInfoPicture);
                final Button cart = (Button) dialogView.findViewById(R.id.btnInfoCart);

                itemName.setText(adapter.getName(position));
                itemPrice.setText("$"+adapter.getPrice(position));
                restName.setText(adapter.getRestaurant(position));
                itemDesc.setText(adapter.getDescription(position));
                restDist.setText(adapter.getDistance(position)+" miles");
                final int num = adapter.getQuantity(position);
                Glide.with(FragmentDeals.this.getContext()).load(adapter.getImage(position)).into(itemPic);

                String[] n = new String[num];
                for(int i = 0; i < num; i++){
                    n[i] = ""+(i+1);
                }

                final Spinner mspin=(Spinner) dialogView.findViewById(R.id.spnInfoQuantity);
                final ArrayAdapter<String> sAdapter = new ArrayAdapter<String>(FragmentDeals.this.getContext(),android.R.layout.simple_spinner_item, n);
                mspin.setAdapter(sAdapter);

                dialogBuilder.setView(dialogView);
                final AlertDialog dialog = dialogBuilder.create();
                dialog.show();

                cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int amount = Integer.parseInt(mspin.getSelectedItem().toString());
                        int newNum = num - amount;
                        int cartAmount = 0;
                        adapter.setQuantity(position, newNum);

                        boolean exists = false;
                        for(int i = 0; i < cartNames.size(); i++){
                            if((cartNames.get(i)).equals(adapter.getName(position))){
                                exists = true;
                                cartAmount = cartQuantities.get(i);
                            }
                        }

                        SQLiteDatabase dbCart = dbHelperCart.getWritableDatabase();
                        ContentValues values = new ContentValues();

                        if(exists){
                            cartAmount += amount;
                            values.put("quantity", ""+cartAmount);

                            String[] selectionArgs = {adapter.getName(position)};

                            dbCart.update("cart", values, "name = ?", selectionArgs);
                        }
                        else{
                            values.put("id", "1");
                            values.put("name", adapter.getName(position));
                            values.put("restaurant", adapter.getRestaurant(position));
                            values.put("address", adapter.getDistance(position));
                            values.put("description", adapter.getDescription(position));
                            values.put("price", adapter.getPrice(position));
                            values.put("image", adapter.getImage(position));
                            values.put("quantity", mspin.getSelectedItemPosition()+1);
                            dbCart.insert("cart", null, values);
                        }

                        dbCart.close();

                        if(newNum == 0){
                            adapter.deleteItem(position);
                        }
                        dialog.dismiss();
                    }
                });
            }
        });

        goToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentShoppingCart fragmentShoppingCart = new FragmentShoppingCart();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, fragmentShoppingCart).commit();
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        dbHelperDeals.close();
        dbHelperCart.close();
        super.onDestroy();
    }
}
