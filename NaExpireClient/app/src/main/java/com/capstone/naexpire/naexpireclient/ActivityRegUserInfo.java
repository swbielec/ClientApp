package com.capstone.naexpire.naexpireclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ActivityRegUserInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_user_info);
        setTitle("Register"); //set activity title
    }

    public void clickNext(View view){
        Intent intent = new Intent(this, ActivityRegFoodTypes.class);
        startActivity(intent);
    }
}
