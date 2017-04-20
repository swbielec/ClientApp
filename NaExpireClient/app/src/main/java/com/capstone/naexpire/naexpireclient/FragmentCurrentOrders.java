package com.capstone.naexpire.naexpireclient;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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


public class FragmentCurrentOrders extends Fragment {
    DatabaseHelperCurrentOrder dbHelperCurrent = null;

    ListAdapterOrdersCurrent adapter;

    public FragmentCurrentOrders() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_orders, container, false);

        FragmentCurrentOrders.this.getActivity().setTitle("Current Orders"); //set title

        Button past = (Button) view.findViewById(R.id.btnOrdersPast);

        adapter = new ListAdapterOrdersCurrent(FragmentCurrentOrders.this.getContext());
        ListView listview = (ListView) view.findViewById(R.id.lstOrdersCurrent);
        listview.setAdapter(adapter);

        //set up connection to current orders database
        dbHelperCurrent = new DatabaseHelperCurrentOrder(getActivity().getApplicationContext());
        SQLiteDatabase dbCurrent = dbHelperCurrent.getReadableDatabase();

        //get all the entire current orders database
        Cursor result = dbCurrent.rawQuery("SELECT * FROM currentOrders", null);

        //add each current order to the list adapter
        while(result.moveToNext()){
            adapter.newItem(result.getString(1),result.getString(2),result.getString(7),
                        result.getString(5), result.getString(8), result.getString(3), result.getString(6));
        }

        dbCurrent.close();
        result.close();

        //'tab' button to view past orders is tapped
        //brings user to past orders fragment
        past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentPastOrders fragmentPastOrders = new FragmentPastOrders();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, fragmentPastOrders).commit();
            }
        });

        //when an item in the list is tapped build a dialog showing info about the order
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

                itemName.setText(adapter.getItemName(position));
                orderprices.setText(adapter.getPrice(position));
                restName.setText(adapter.getRestaurantName(position));
                Random rnd = new Random();
                orderid.setText("Order #"+(100000 + rnd.nextInt(900000)));
                ordertotal.setText("$"+adapter.getTotal(position));
                ordertime.setText("Placed at: "+adapter.getTime(position));

                dialogBuilder.setView(dialogView);
                final AlertDialog dialog = dialogBuilder.create();
                dialog.show();

                //get directions button tapped
                //opens Google Maps to show where the restaurant is
                orderDirec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String uri = "geo:0,0?q=7349 E Diamond St";
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                        startActivity(intent);
                    }
                });

                //call restaurant button tapped
                //dials in the restaurant phone number, but does not auto-initiate the call
                orderCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String uri = "tel: 0123456789";
                        Intent intent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse(uri));
                        startActivity(intent);
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        dbHelperCurrent.close();
        super.onDestroy();
    }
}
