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


public class FragmentCurrentOrders extends Fragment {

    ListAdapterDiscounts adapter;
    ArrayList<String> restname = new ArrayList<String>();
    ArrayList<String> time = new ArrayList<String>();
    ArrayList<String> price = new ArrayList<String>();

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

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id){
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(FragmentCurrentOrders.this.getContext());
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_current_order, null);
                final TextView itemName = (TextView) dialogView.findViewById(R.id.lblCurrentName);
                final TextView restName = (TextView) dialogView.findViewById(R.id.lblCurrentRest);
                final TextView itemPrice = (TextView) dialogView.findViewById(R.id.lblCurrentPrices);
                final TextView itemDesc = (TextView) dialogView.findViewById(R.id.lbl);
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
