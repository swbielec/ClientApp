package com.capstone.naexpire.naexpireclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapterFoods extends BaseAdapter{

    ArrayList<String> types;
    Context context;
    Holder holder;

    private static LayoutInflater inflater = null;

    public ListAdapterFoods(Context c, ArrayList<String> t){
        types = t;
        context = c;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return types.size();
    }

    @Override
    public Object getItem(int position){
        return position;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    public void clicked(int position){
        holder.bt.setImageResource(R.mipmap.ic_check_box_black_24dp);
        notifyDataSetChanged();
    }

    public class Holder{
        TextView tp;
        ImageView bt;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        holder = new Holder();
        final View rowView = inflater.inflate(R.layout.list_foods, null);
        holder.tp=(TextView) rowView.findViewById(R.id.lblListFoods);
        holder.bt=(ImageView) rowView.findViewById(R.id.imgCheckBox);

        holder.tp.setText(types.get(position));

        return rowView;
    }
}