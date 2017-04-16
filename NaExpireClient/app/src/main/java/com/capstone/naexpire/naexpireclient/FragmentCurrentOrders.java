package com.capstone.naexpire.naexpireclient;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    ArrayList<String> itemName = new ArrayList<String>();
    ArrayList<String> restaurantName = new ArrayList<String>();
    ArrayList<String> time = new ArrayList<String>();
    ArrayList<String> price = new ArrayList<String>();
    ArrayList<String> image = new ArrayList<String>();
    ArrayList<String> address = new ArrayList<String>();

    public FragmentCurrentOrders() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_orders, container, false);

        dbHelperCurrent = new DatabaseHelperCurrentOrder(getActivity().getApplicationContext());

        FragmentCurrentOrders.this.getActivity().setTitle("Current Orders"); //set title

        adapter = new ListAdapterOrdersCurrent(FragmentCurrentOrders.this.getContext());
        ListView listview = (ListView) view.findViewById(R.id.lstOrdersCurrent);
        listview.setAdapter(adapter);

        //dummy data
        /*itemname.add("Spaghetti\nBreadsticks");
        itemname.add("Cajun Coffee\nGyros");
        itemname.add("Spring Rolls\nShaken Beef\nSake");
        prices.add("9.32,3.04");
        prices.add("2.21,6.22");
        prices.add("13.12,10.20,3.07");
        restname.add("McFate Brewery");
        restname.add("Haji Baba");
        restname.add("Rice Paper");
        time.add("3:45pm 2/24/17");
        time.add("1:23pm 2/24/17");
        time.add("9:39am 2/24/17");
        distance.add(12.5);
        distance.add(3.5);
        distance.add(7.3);
        image.add("android.resource://com.capstone.naexpire.naexpireclient/drawable/carbonara");
        image.add("android.resource://com.capstone.naexpire.naexpireclient/drawable/gyros");
        image.add("android.resource://com.capstone.naexpire.naexpireclient/drawable/rolls");*/

        SQLiteDatabase dbCurrent = dbHelperCurrent.getReadableDatabase();

        Cursor result = dbCurrent.rawQuery("SELECT * FROM currentOrders", null);

        while(result.moveToNext()){

            adapter.newItem(result.getString(1),result.getString(2),result.getString(7),
                        result.getString(5), "now", result.getString(3), result.getString(6));
        }

        dbCurrent.close();
        result.close();

        /*for(int i = 0; i < price.size(); i++){
            adapter.newItem(itemName.get(i), restaurantName.get(i), image.get(i), price.get(i),
                    time.get(i), address.get(i), quantity.get(i));
        }*/

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

    @Override
    public void onDestroy() {
        dbHelperCurrent.close();
        super.onDestroy();
    }
}
