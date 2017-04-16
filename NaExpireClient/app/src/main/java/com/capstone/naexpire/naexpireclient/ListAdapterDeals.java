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

public class ListAdapterDeals extends BaseAdapter {

    ArrayList<Discount> discounts;
    Context context;

    private static LayoutInflater inflater = null;

    public ListAdapterDeals(Context c){
        discounts = new ArrayList<>();
        context = c;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return discounts.size();
    }

    @Override
    public Object getItem(int position){
        return position;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    public String getName(int position){ return discounts.get(position).getName(); }
    public Double getPrice(int position){ return discounts.get(position).getPrice(); }
    public String getRestaurant(int position){ return discounts.get(position).getRestaurant(); }
    public String getImage(int position){ return discounts.get(position).getImage(); }
    public String getDescription(int position){ return discounts.get(position).getDescription(); }
    public Double getDistance(int position){ return discounts.get(position).getDistance(); }
    public int getQuantity(int position){ return discounts.get(position).getQuantity(); }

    public void setQuantity(int position, int quantity){ discounts.get(position).setQuantity(quantity);}

    public void deleteItem(int position){
        discounts.remove(position);
        notifyDataSetChanged();
    }

    public void setItem(int position, String name, Double price, String restaurant, String image,
                        String description, Double distance, int quantity){
        discounts.get(position).getName();
        discounts.get(position).getPrice();
        discounts.get(position).getRestaurant();
        discounts.get(position).getImage();
        notifyDataSetChanged();
    }

    public void newItem(String name, Double price, String restaurant, String image,
                        String description, Double distance, int quantity){
        discounts.add(new Discount(name, price, restaurant, image, description, distance, quantity));
        notifyDataSetChanged();
    }

    public void sortDiscounts(int sortBy){
        switch (sortBy){
            case 0: //alphabetical
                Collections.sort(discounts, new Comparator<Discount>(){
                    public int compare( Discount o1, Discount o2){
                        return o1.getName().compareTo(o2.getName());
                    }
                });
                notifyDataSetChanged();
                break;
            case 1: //Distance
                Collections.sort(discounts, new Comparator<Discount>(){
                    public int compare( Discount o1, Discount o2){
                        return o1.getDistance().compareTo(o2.getDistance());
                    }
                });
                notifyDataSetChanged();
                break;
            case 2: //high to low
                Collections.sort(discounts, new Comparator<Discount>(){
                    public int compare( Discount o1, Discount o2){
                        return -1*o1.getPrice().compareTo(o2.getPrice());
                    }
                });
                notifyDataSetChanged();
                break;
            case 3: //low to high
                Collections.sort(discounts, new Comparator<Discount>(){
                    public int compare( Discount o1, Discount o2){
                        return o1.getPrice().compareTo(o2.getPrice());
                    }
                });
                notifyDataSetChanged();
                break;
        }
    }

    public class Holder{
        TextView in, rn, pr, dl;
        ImageView im;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        Holder holder = new ListAdapterDeals.Holder();
        final View rowView = inflater.inflate(R.layout.list_deals, null);
        holder.in=(TextView) rowView.findViewById(R.id.lblDiscountName);
        holder.rn=(TextView) rowView.findViewById(R.id.lblDiscountRest);
        holder.pr=(TextView) rowView.findViewById(R.id.lblDiscountPrice);
        holder.dl=(TextView) rowView.findViewById(R.id.lblDiscountQuantity);
        holder.im=(ImageView) rowView.findViewById(R.id.imgDiscountPicture);

        holder.in.setText(discounts.get(position).getName());
        holder.rn.setText(discounts.get(position).getRestaurant());
        holder.pr.setText("$"+discounts.get(position).getPrice());
        holder.dl.setText(discounts.get(position).getQuantity()+"x");
        Glide.with(context).load(discounts.get(position).getImage()).into(holder.im);

        return rowView;
    }

    public class Discount{
        private String name, restaurant, image, description;
        private double distance, price;
        private int quantity;

        Discount(){
            name = "";
            price = 0.00;
            restaurant = "";
            image = "";
            description = "";
            distance = 0.0;
            quantity = 0;
        }

        Discount(String n, Double p, String r, String i, String d, Double dist, int q){
            name = n;
            price  = p;
            restaurant = r;
            image = i;
            description = d;
            distance = dist;
            quantity = q;
        }
        String getName(){return name;}
        Double getPrice(){return price;}
        String getRestaurant(){return restaurant;}
        String getImage(){return image;}
        String getDescription(){return description;}
        Double getDistance(){return distance;}
        int getQuantity(){return quantity;}

        void setName(String n){name = n;}
        void setPrice(Double p){price = p;}
        void setRestaurant(String r){restaurant = r;}
        void setImage(String i){image = i;}
        void setDescription(String d){description = d;}
        void setDistance(Double d){distance = d;}
        void setQuantity(int q){quantity = q;}
    }
}