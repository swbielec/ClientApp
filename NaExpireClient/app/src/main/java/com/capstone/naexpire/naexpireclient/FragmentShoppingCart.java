package com.capstone.naexpire.naexpireclient;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FragmentShoppingCart extends Fragment {
    DatabaseHelperCart dbHelperCart = null;
    DatabaseHelperCurrentOrder dbHelperCurrent = null;

    ListAdapterCart adapter;
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> restname = new ArrayList<String>();
    ArrayList<String> price = new ArrayList<String>();
    ArrayList<String> quantity = new ArrayList<String>();
    TextView subtotal;
    Button toDeals, placeOrder;


    public FragmentShoppingCart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);

        dbHelperCart = new DatabaseHelperCart(getActivity().getApplicationContext());
        dbHelperCurrent = new DatabaseHelperCurrentOrder(getActivity().getApplicationContext());

        toDeals = (Button) view.findViewById(R.id.btnCartBack);
        placeOrder = (Button) view.findViewById(R.id.btnCartPlace);

        FragmentShoppingCart.this.getActivity().setTitle("Shopping Cart"); //set activity title

        adapter = new ListAdapterCart(FragmentShoppingCart.this.getContext(), FragmentShoppingCart.this);
        ListView listview = (ListView) view.findViewById(R.id.lstCart);
        listview.setAdapter(adapter);

        SQLiteDatabase db = dbHelperCart.getReadableDatabase();

        Cursor result = db.rawQuery("SELECT id, name, restaurant, address, description, price, quantity, image FROM cart", null);

        while(result.moveToNext()){
            name.add(result.getString(1));
            restname.add(result.getString(2));
            price.add("$"+result.getString(5));
            quantity.add(result.getString(6));
            adapter.newItem(result.getString(1), result.getString(2), result.getString(7),
                    Double.parseDouble(result.getString(5)), Integer.parseInt(result.getString(6)));
        }

        db.close();
        result.close();

        subtotal = (TextView) view.findViewById(R.id.lblCartSubtotal);
        subtotal.setText(adapter.updateSubtotal());

        toDeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentDeals fragmentDeals = new FragmentDeals();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, fragmentDeals).commit();
            }
        });

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
                Date date = new Date();
                String dateTime = dateFormat.format(date);

                //put all info in current orders db
                SQLiteDatabase dbCurrent = dbHelperCurrent.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("id", "1");
                values.put("name", adapter.getAllItemNames());
                values.put("restaurant", adapter.getAllRestaurantNames());
                //values.put("address", adapter.getDistance(position));
                values.put("time", dateTime);
                values.put("price", adapter.getAllPrices());
                values.put("image", adapter.getImage(0));
                values.put("quantity", adapter.getAllQuantities());
                dbCurrent.insert("currentOrders", null, values);
                dbCurrent.close();

                //clear shopping cart
                SQLiteDatabase dbCart = dbHelperCart.getWritableDatabase();
                dbCart.delete("cart", null, null);
                dbCart.close();

                FragmentCurrentOrders fragmentCurrentOrders = new FragmentCurrentOrders();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, fragmentCurrentOrders).commit();
            }
        });

        return view;
    }

    public void setSubtotal(){
        subtotal.setText(adapter.updateSubtotal());
    }

    public void deleteItem(int position){
        SQLiteDatabase db = dbHelperCart.getWritableDatabase();
        String[] selectionArgs = {adapter.getItemName(position)};

        db.delete("cart", "name = ?", selectionArgs);

        db.close();

        Toast.makeText(FragmentShoppingCart.this.getContext(), selectionArgs[0] + " removed from cart.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        dbHelperCart.close();
        dbHelperCurrent.close();
        super.onDestroy();
    }
}
