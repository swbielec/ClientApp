package com.capstone.naexpire.naexpireclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapterDiscounts extends BaseAdapter {

    ArrayList<String> iname, rname, price;
    Context context;
    ListAdapterDiscounts.Holder holder;

    private static LayoutInflater inflater = null;

    public ListAdapterDiscounts(Context c, ArrayList<String> i, ArrayList<String> r, ArrayList<String> p){
        iname = i;
        rname = r;
        price = p;
        context = c;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return iname.size();
    }

    @Override
    public Object getItem(int position){
        return position;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    public class Holder{
        TextView in, rn, pr;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        holder = new ListAdapterDiscounts.Holder();
        final View rowView = inflater.inflate(R.layout.list_discounts, null);
        holder.in=(TextView) rowView.findViewById(R.id.lblDiscountName);
        holder.rn=(TextView) rowView.findViewById(R.id.lblDiscountRest);
        holder.pr=(TextView) rowView.findViewById(R.id.lblDiscountPrice);

        holder.in.setText(iname.get(position));
        holder.rn.setText(rname.get(position));
        holder.pr.setText(price.get(position));

        return rowView;
    }
}