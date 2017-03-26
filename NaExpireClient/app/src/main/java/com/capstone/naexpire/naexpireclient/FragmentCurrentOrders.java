package com.capstone.naexpire.naexpireclient;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import java.util.Random;


public class FragmentCurrentOrders extends Fragment {

    ListAdapterDiscounts adapter;
    ArrayList<String> itemname = new ArrayList<String>();
    ArrayList<String> restname = new ArrayList<String>();
    ArrayList<String> time = new ArrayList<String>();
    ArrayList<String> price = new ArrayList<String>();
    ArrayList<String> prices = new ArrayList<String>();

    public FragmentCurrentOrders() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_orders, container, false);
        FragmentCurrentOrders.this.getActivity().setTitle("Current Orders"); //set title

        //dummy data
        itemname.add("Spaghetti\nBreadsticks");
        itemname.add("Cajun Coffee\nGyros");
        itemname.add("Spring Rolls\nShaken Beef\nSake");
        prices.add("$9.32\n$3.04");
        prices.add("$2.21\n$6.22");
        prices.add("$13.12\n$10.20\n$3.07");
        restname.add("McFate Brewery");
        restname.add("Haji Baba");
        restname.add("Rice Paper");
        time.add("3:45pm 2/24/17");
        time.add("1:23pm 2/24/17");
        time.add("9:39am 2/24/17");
        price.add("$12.36");
        price.add("$8.43");
        price.add("$16.39");

        adapter = new ListAdapterDiscounts(FragmentCurrentOrders.this.getContext(), restname, time, price);
        ListView listview = (ListView) view.findViewById(R.id.lstOrdersCurrent);
        listview.setAdapter(adapter);

        Button past = (Button) view.findViewById(R.id.btnOrdersPast);

        past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentPastOrders fragmentPastOrders = new FragmentPastOrders();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, fragmentPastOrders).commit();
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id){
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(FragmentCurrentOrders.this.getContext());
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_current_order, null);
                final TextView itemName = (TextView) dialogView.findViewById(R.id.lblCurrentName);
                final TextView restName = (TextView) dialogView.findViewById(R.id.lblCurrentRest);
                final TextView orderprices = (TextView) dialogView.findViewById(R.id.lblCurrentPrices);
                final TextView orderid = (TextView) dialogView.findViewById(R.id.lblCurrentOrderID);
                final TextView ordertotal = (TextView) dialogView.findViewById(R.id.lblCurrentTotal);
                final TextView ordertime = (TextView) dialogView.findViewById(R.id.lblCurrentTime);
                Button orderDirec = (Button) dialogView.findViewById(R.id.btnCurrentDirections);
                Button orderCall = (Button) dialogView.findViewById(R.id.btnCurrentCall);

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

                orderDirec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                orderCall.setOnClickListener(new View.OnClickListener() {
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
