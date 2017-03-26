package com.capstone.naexpire.naexpireclient;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class FragmentShoppingCart extends Fragment {

    ListAdapterCart adapter;
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> restname = new ArrayList<String>();
    ArrayList<String> price = new ArrayList<String>();
    ArrayList<String> quantity = new ArrayList<String>();
    TextView subtotal;


    public FragmentShoppingCart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);

        FragmentShoppingCart.this.getActivity().setTitle("Shopping Cart"); //set activity title

        //dummy data
        name.add("Shrimp Taco");
        name.add("Potato Soup");
        name.add("Cheeseburger");
        restname.add("Senor Taco");
        restname.add("Subway");
        restname.add("In n Out");
        price.add("$5.34");
        price.add("$8.34");
        price.add("$2.78");
        quantity.add("3");
        quantity.add("2");
        quantity.add("1");

        subtotal = (TextView) view.findViewById(R.id.lblCartSubtotal);
        subtotal.setText(updateSubtotal());

        adapter = new ListAdapterCart(FragmentShoppingCart.this.getContext(), name, restname, price, quantity, subtotal);
        ListView listview = (ListView) view.findViewById(R.id.lstCart);
        listview.setAdapter(adapter);

        return view;
    }

    public String updateSubtotal(){
        double sum = 0.00;
        for(int i = 0; i < price.size(); i++){
            sum += Double.parseDouble(price.get(i).substring(1));
        }
        return "Subtotal: $"+sum;
    }

}
