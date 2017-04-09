package com.capstone.naexpire.naexpireclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListAdapterOrdersPast extends BaseAdapter{
    ArrayList<Order> orders;
    Context context;

    private static LayoutInflater inflater = null;

    public ListAdapterOrdersPast(Context c){
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

    public String getOrderId(int position){return orders.get(position).getOrderId();}
    public String getName(int position){ return orders.get(position).getName(); }
    public String getRestaurant(int position){ return orders.get(position).getRestaurant(); }
    public String getPrice(int position){ return orders.get(position).getPrice(); }
    public String getTime(int position){ return orders.get(position).getTime(); }
    public String getTotal(int position){ return "$"+orders.get(position).getTotal(); }
    public int getRating(int position){ return orders.get(position).getRating(); }

    public void setRating(int position, int stars){orders.get(position).setRating(stars);}

    public void newItem(String id, String name, String restaurant, String price, String time) {
        orders.add(new Order(id, name, restaurant, price, time));
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
        TextView id, rn, tl;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        Holder holder = new Holder();
        final View rowView = inflater.inflate(R.layout.list_orders_past, null);
        holder.id=(TextView) rowView.findViewById(R.id.lblPastListId);
        holder.rn=(TextView) rowView.findViewById(R.id.lblPastListRest);
        holder.tl=(TextView) rowView.findViewById(R.id.lblPastListTotal);

        holder.id.setText("Order #"+orders.get(position).getOrderId());
        holder.rn.setText(orders.get(position).getRestaurant());
        holder.tl.setText("$"+orders.get(position).getTotal());

        return rowView;
    }

    public class Order{
        private String orderId, names, restaurant, price, time;
        private double total;
        private int rating;

        Order(){
            orderId = "";
            names = "";
            restaurant = "";
            price = "";
            time = "";
            total = 0.00;
            rating = 0;
        }

        Order(String id, String name, String restName, String prices, String timePlaced){
            orderId = id;
            names = name;
            restaurant = restName;
            price  = prices;
            time = timePlaced;
            String[] strPrices = price.split(",");
            for(int q = 0; q < strPrices.length; q++){
                total += Double.parseDouble(strPrices[q]);
            }
            rating = 0;
        }

        String getOrderId(){return orderId;}
        String getName(){return names;}
        String getRestaurant(){return restaurant;}
        String getPrice(){
            String[] s = price.split(",");
            String formattedPrice = "";
            for(int t = 0; t< s.length;t++){
                formattedPrice += "$"+s[t]+"\n";
            }
            return formattedPrice;
        }
        String getTime(){return time;}
        Double getTotal(){return total;}
        int getRating(){return rating;}

        void setName(String n){names = n;}
        void setRestaurant(String r){restaurant = r;}
        void setPrice(String p){price = p;}
        void setTotal(double t){total = t;}
        void setRating(int stars){rating = stars;}
    }
}
