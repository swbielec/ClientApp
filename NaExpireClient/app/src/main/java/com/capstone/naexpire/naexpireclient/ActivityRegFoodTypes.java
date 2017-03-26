package com.capstone.naexpire.naexpireclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ActivityRegFoodTypes extends AppCompatActivity {

    ArrayList<String> foods = new ArrayList<String>();
    ArrayList<String> selected = new ArrayList<String>(); //list of currently selected food types
    ListAdapterFoods adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_food_types);
        setTitle("Register"); //set activity title

        //dummy data
        foods.add("Mexican");
        foods.add("Cajun");
        foods.add("Vietnamese");
        foods.add("Chinese");
        foods.add("Mediterranean");

        adapter = new ListAdapterFoods(this, foods);
        final ListView listView = (ListView) findViewById(R.id.lstRegFoodsList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //adapter.clicked(position);

                /*String sel = listView.getItemAtPosition(position).toString(); //gets text of selected element
                if(isIn(sel)){ //deselect if tapped when selected
                    parent.getChildAt(position).setBackgroundColor(Color.TRANSPARENT);
                    selected.remove(sel); //remove from list of selected food types
                }
                else{ //select if tapped when deselected
                    //change background color to accent color
                    listView.getChildAt(position).setBackgroundColor(Color.argb(255,255,64,129));
                    selected.add(sel); //add to list of selected food types
                }
                adapter.notifyDataSetChanged();*/
            }
        });
    }

    public void clickFinish(View view){
        Intent intent = new Intent(this, ActivityLogin.class);
        startActivity(intent);
    }

    public Boolean isIn(String food){
        for(int i = 0; i < selected.size(); i++){
            if (food.equals(selected.get(i))) return true;
        }
        return false;
    }
}
