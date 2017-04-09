package com.capstone.naexpire.naexpireclient;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class FragmentPastOrders extends Fragment {

    ListAdapterDiscounts adapter;
    ArrayList<String> itemname = new ArrayList<String>();
    ArrayList<String> restname = new ArrayList<String>();
    ArrayList<String> time = new ArrayList<String>();
    ArrayList<String> price = new ArrayList<String>();
    ArrayList<String> prices = new ArrayList<String>();

    public FragmentPastOrders() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_past_orders, container, false);
        FragmentPastOrders.this.getActivity().setTitle("Past Orders"); //set title

        //dummy data
        itemname.add("Tamales\nFlan");
        itemname.add("Cheese Pizza\nSoft Drink");
        itemname.add("Scrambled Eggs\nHorchata\nPancakes");
        prices.add("$11.52\n$5.34");
        prices.add("$8.98\n$1.23");
        prices.add("$13.12\n$4.23\n$3.07");
        restname.add("Los Sombreros");
        restname.add("Organ Stop Pizza");
        restname.add("Otro Cafe");
        time.add("12:42pm 2/22/17");
        time.add("9:17am 2/19/17");
        time.add("2:32pm 2/03/17");
        price.add("$16.86");
        price.add("$11.21");
        price.add("$20.42");

        adapter = new ListAdapterDiscounts(FragmentPastOrders.this.getContext());
        ListView listview = (ListView) view.findViewById(R.id.lstOrdersPast);
        listview.setAdapter(adapter);

        Button current = (Button) view.findViewById(R.id.btnOrdersCurrent);

        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentCurrentOrders fragmentCurrentOrders = new FragmentCurrentOrders();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, fragmentCurrentOrders).commit();
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id){
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(FragmentPastOrders.this.getContext());
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_past_order, null);
                final TextView itemName = (TextView) dialogView.findViewById(R.id.lblPastName);
                final TextView restName = (TextView) dialogView.findViewById(R.id.lblPastRest);
                final TextView orderprices = (TextView) dialogView.findViewById(R.id.lblPastPrices);
                final TextView orderid = (TextView) dialogView.findViewById(R.id.lblPastOrderID);
                final TextView ordertotal = (TextView) dialogView.findViewById(R.id.lblPastTotal);
                final TextView ordertime = (TextView) dialogView.findViewById(R.id.lblPastTime);
                Button orderSubmit = (Button) dialogView.findViewById(R.id.btnPastSubmit);

                itemName.setText(itemname.get(position));
                orderprices.setText(prices.get(position));
                restName.setText(restname.get(position));
                Random rnd = new Random();
                orderid.setText("Order #"+(100000 + rnd.nextInt(900000)));
                ordertotal.setText(price.get(position));
                ordertime.setText("Placed at: "+time.get(position));

                dialogBuilder.setView(dialogView);
                final AlertDialog dialog = dialogBuilder.create();
                dialog.show();

                orderSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

        return view;
    }

}
