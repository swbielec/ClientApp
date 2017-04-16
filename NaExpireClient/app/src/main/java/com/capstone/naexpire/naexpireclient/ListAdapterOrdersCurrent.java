package com.capstone.naexpire.naexpireclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListAdapterOrdersCurrent extends BaseAdapter {
    ArrayList<Order> orders;
    Context context;

    private static LayoutInflater inflater = null;

    public ListAdapterOrdersCurrent(Context c){
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

    public String getItemName(int position){ return orders.get(position).getItemName(); }
    public String getRestaurantName(int position){ return orders.get(position).getRestaurantName(); }
    public String getImage(int position){ return orders.get(position).getImage(); }
    public String getPrice(int position){ return orders.get(position).getPrice(); }
    public String getTime(int position){ return orders.get(position).getTime(); }
    public String getAddress(int position){ return orders.get(position).getAddress(); }
    public double getTotal(int position){ return orders.get(position).getTotal(); }

    public void newItem(String itemName, String restaurantName, String image, String price, String time,
                        String address, String quantity) {
        orders.add(new Order(itemName, restaurantName, image, price, time, address, quantity));
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
        final View rowView = inflater.inflate(R.layout.list_orders_current, null);
        holder.rn=(TextView) rowView.findViewById(R.id.lblOrdersRest);
        holder.tm=(TextView) rowView.findViewById(R.id.lblOrdersTime);
        holder.pr=(TextView) rowView.findViewById(R.id.lblOrdersPrice);
        holder.im=(ImageView) rowView.findViewById(R.id.imgOrdersPic);

        holder.rn.setText(orders.get(position).getRestaurantName());
        holder.tm.setText(orders.get(position).getTime());
        holder.pr.setText("$"+orders.get(position).getTotal());
        Glide.with(context).load(orders.get(position).getImage()).into(holder.im);

        return rowView;
    }

    public class Order{
        private String itemName, restaurantName, image, price, time, address, quantity;
        private double total;

        Order(){
            itemName = "";
            restaurantName = "";
            image = "";
            address = "";
            price = "";
            time = "";
            total = 0.00;
            quantity = "";
        }

        Order(String itemName, String restaurantName, String image, String price, String time,
              String address, String quantity){
            this.itemName = itemName;
            this.restaurantName = restaurantName;
            this.image = image;
            this.price  = price;
            this.time = time;
            this.address = address;
            this.quantity = quantity;
            String[] strPrices = price.split(",");
            String[] quantities = quantity.split(",");
            total = 0.0;
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            for(int q = 0; q < strPrices.length; q++){
                total += Double.parseDouble(strPrices[q]) * Integer.parseInt(quantities[q]);
            }
            total = Double.parseDouble(decimalFormat.format(total));
        }
        String getItemName(){
            String[] s = itemName.split(",");
            String[] q = quantity.split(",");
            String result = q[0]+"x "+s[0];
            if(s.length == 1) return result;
            else{
                for(int i = 1; i < s.length; i++){
                    result += "\n"+q[i]+"x "+s[i];
                }
                return result;
            }
        }
        String getRestaurantName(){
            String[] s = restaurantName.split(",");
            if (s.length > 1) return s[0]+" +"+(s.length-1)+" more";
            else return s[0];
        }
        String getImage(){return image;}
        String getPrice(){
            String[] s = price.split(",");
            String formattedPrice = "$"+s[0];
            if(s.length == 1) return formattedPrice;
            else{
                for(int t = 1; t< s.length;t++){
                    formattedPrice += "\n$"+s[t];
                }
                return formattedPrice;
            }
        }
        String getTime(){return time;}
        String getAddress(){return address;}
        String getQuantity(){return quantity;}
        Double getTotal(){return total;}

        void setItemName(String itemName){this.itemName = itemName;}
        void setRestaurantName(String restaurantName){this.restaurantName = restaurantName;}
        void setImage(String image){this.image = image;}
        void setPrice(String price){this.price = price;}
        void setAddress(String address){this.address = address;}
        void setQuantity(String quantity){this.quantity = quantity;}
        void setTotal(double t){total = t;}
    }
}
