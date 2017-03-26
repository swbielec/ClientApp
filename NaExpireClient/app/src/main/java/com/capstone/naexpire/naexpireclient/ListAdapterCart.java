package com.capstone.naexpire.naexpireclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListAdapterCart extends BaseAdapter {

    ArrayList<String> iname, rname, price, quantity;
    TextView subtotal;
    Context context;
    ListAdapterCart.Holder holder;

    private static LayoutInflater inflater = null;

    public ListAdapterCart(Context c, ArrayList<String> i, ArrayList<String> r, ArrayList<String> p,
                           ArrayList<String> q, TextView s) {
        iname = i;
        rname = r;
        price = p;
        quantity = q;
        subtotal = s;
        context = c;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return iname.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView in, rn, pr, qu;
        ImageButton ex;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        holder = new ListAdapterCart.Holder();
        final View rowView = inflater.inflate(R.layout.list_shopping_cart, null);
        holder.in = (TextView) rowView.findViewById(R.id.lblCartName);
        holder.rn = (TextView) rowView.findViewById(R.id.lblCartRestName);
        holder.pr = (TextView) rowView.findViewById(R.id.lblCartPrice);
        holder.qu = (TextView) rowView.findViewById(R.id.lblCartQuantity);
        holder.ex = (ImageButton) rowView.findViewById(R.id.imgbtnCartDelete);

        holder.in.setText(iname.get(position));
        holder.rn.setText(rname.get(position));
        holder.pr.setText(price.get(position));
        holder.qu.setText(quantity.get(position)+"x");

        holder.ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iname.remove(position);
                rname.remove(position);
                price.remove(position);
                quantity.remove(position);
                notifyDataSetChanged();
                subtotal.setText(updateSubtotal());
            }
        });

        return rowView;
    }

    public String updateSubtotal(){
        double sum = 0.00;
        for(int i = 0; i < price.size(); i++){
            sum += Double.parseDouble(price.get(i).substring(1));
        }
        return "Subtotal: $"+sum;
    }
}