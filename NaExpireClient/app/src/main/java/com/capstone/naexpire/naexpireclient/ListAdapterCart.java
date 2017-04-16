package com.capstone.naexpire.naexpireclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ListAdapterCart extends BaseAdapter {

    ArrayList<Item> items;
    FragmentShoppingCart cart;
    Context context;
    ListAdapterCart.Holder holder;

    private static LayoutInflater inflater = null;

    public ListAdapterCart(Context c, FragmentShoppingCart cart) {
        items = new ArrayList<>();
        this.cart = cart;
        context = c;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
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

        holder.in.setText(items.get(position).getItemName());
        holder.rn.setText(items.get(position).getRestaurantName());
        holder.pr.setText("$"+items.get(position).getPrice());
        holder.qu.setText(items.get(position).getQuantity()+"x");

        holder.ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(position);
                cart.setSubtotal();
            }
        });

        return rowView;
    }

    public String updateSubtotal(){
        double sum = 0.00;
        for(int i = 0; i < items.size(); i++){
            sum += items.get(i).getPrice() * items.get(i).getQuantity();
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return "Subtotal: $"+decimalFormat.format(sum);
    }

    public void newItem(String itemName, String restaurantName, double price, int quantity){
        items.add(new Item(itemName, restaurantName, price, quantity));
        notifyDataSetChanged();
    }

    public String getItemName(int position){return items.get(position).getItemName();}
    public String getRestaurantName(int position){return items.get(position).getRestaurantName();}
    public double getPrice(int position){return items.get(position).getPrice();}
    public int getQuantity(int position){return items.get(position).getQuantity();}

    public String getAllItemNames(){
        String names = "";
        if(items.size()>0) names += items.get(0).getItemName();
        for(int i = 1; i < items.size(); i++){
            names += ","+items.get(i).getItemName();
        }
        return names;
    }
    public String getAllRestaurantNames(){
        String names = "";
        if(items.size()>0) names += items.get(0).getRestaurantName();
        for(int i = 1; i < items.size(); i++){
            names += ","+items.get(i).getRestaurantName();
        }
        return names;
    }
    public String getAllPrices(){
        String prices = "";
        if(items.size()>0) prices += items.get(0).getPrice();
        for(int i = 1; i < items.size(); i++){
            prices += ","+items.get(i).getPrice();
        }
        return prices;
    }
    public String getAllQuantities(){
        String quantities = "";
        if(items.size()>0) quantities += items.get(0).getQuantity();
        for(int i = 1; i < items.size(); i++){
            quantities += ","+items.get(i).getQuantity();
        }
        return quantities;
    }

    public void setItemName(int position, String itemName){items.get(position).setItemName(itemName);}
    public void setRestaurantName(int position, String restaurantName){items.get(position).setRestaurantName(restaurantName);}
    public void setPrice(int position, double price){items.get(position).setPrice(price);}
    public void setQuantity(int position, int quantity){items.get(position).setQuantity(quantity);}

    public void deleteItem(int position){
        cart.deleteItem(position);
        items.remove(position);
        notifyDataSetChanged();
    }

    public class Item{
        private String itemName, restaurantName;
        private double price;
        private int quantity;

        Item(){
            itemName = "";
            restaurantName = "";
            price = 0.0;
            quantity = 0;
        }

        Item(String itemName, String restaurantName, double price, int quantity){
            this.itemName = itemName;
            this.restaurantName = restaurantName;
            this.price = price;
            this.quantity = quantity;
        }

        String getItemName(){return itemName;}
        String getRestaurantName(){return restaurantName;}
        double getPrice(){return price;}
        int getQuantity(){return quantity;}

        void setItemName(String itemName){this.itemName = itemName;}
        void setRestaurantName(String restaurantName){this.restaurantName = restaurantName;}
        void setPrice(double price){this.price = price;}
        void setQuantity(int quantity){this.quantity = quantity;}
    }
}