package com.capstone.naexpire.naexpireclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListAdapterOrders extends BaseAdapter {
    ArrayList<Order> orders;
    Context context;

    private static LayoutInflater inflater = null;

    public ListAdapterOrders(Context c){
        orders = new ArrayList<>();
        context = c;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return orders.size();
    }

    @Override
    public Object getItem(int position){
        return position;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    public String getName(int position){ return orders.get(position).getName(); }
    public String getRestaurant(int position){ return orders.get(position).getRestaurant(); }
    public String getImage(int position){ return orders.get(position).getImage(); }
    public String getPrice(int position){ return orders.get(position).getPrice(); }
    public String getTime(int position){ return orders.get(position).getTime(); }
    public double getDistance(int position){ return orders.get(position).getDistance(); }
    public double getTotal(int position){ return orders.get(position).getTotal(); }

    public void newItem(String name, String restaurant, String image, String price, String time,
                        double distance) {
        orders.add(new Order(name, restaurant, image, price, time, distance));
        notifyDataSetChanged();
    }

    public void sortOrders() { //sort orders by time
        Collections.sort(orders, new Comparator<Order>() {
            public int compare(Order o1, Order o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });
        notifyDataSetChanged();
    }

    public class Holder{
        TextView rn, tm, pr;
        ImageView im;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        Holder holder = new Holder();
        final View rowView = inflater.inflate(R.layout.list_orders, null);
        holder.rn=(TextView) rowView.findViewById(R.id.lblOrdersRest);
        holder.tm=(TextView) rowView.findViewById(R.id.lblOrdersTime);
        holder.pr=(TextView) rowView.findViewById(R.id.lblOrdersPrice);
        holder.im=(ImageView) rowView.findViewById(R.id.imgOrdersPic);

        holder.rn.setText(orders.get(position).getRestaurant());
        holder.tm.setText(orders.get(position).getTime());
        holder.pr.setText("$"+orders.get(position).getTotal());
        Glide.with(context).load(orders.get(position).getImage()).into(holder.im);

        return rowView;
    }

    public class Order{
        private String names, restaurant, image, price, time;
        private double distance, total;

        Order(){
            names = "";
            restaurant = "";
            image = "";
            distance = 0.0;
            price = "";
            time = "";
            total = 0.00;
        }

        Order(String n, String r, String i, String p, String t, double d){
            names = n;
            restaurant = r;
            image = i;
            price  = p;
            time = t;
            distance = d;
            String[] strPrices = p.split(",");
            for(int q = 0; q < strPrices.length; q++){
                total += Double.parseDouble(strPrices[q]);
            }
        }
        String getName(){return names;}
        String getRestaurant(){return restaurant;}
        String getImage(){return image;}
        String getPrice(){
            String[] s = price.split(",");
            String formattedPrice = "";
            for(int t = 0; t< s.length;t++){
                formattedPrice += "$"+s[t]+"\n";
            }
            return formattedPrice;
        }
        String getTime(){return time;}
        Double getDistance(){return distance;}
        Double getTotal(){return total;}

        void setName(String n){names = n;}
        void setRestaurant(String r){restaurant = r;}
        void setImage(String i){image = i;}
        void setPrice(String p){price = p;}
        void setDistance(Double d){distance = d;}
        void setTotal(double t){total = t;}
    }
}
