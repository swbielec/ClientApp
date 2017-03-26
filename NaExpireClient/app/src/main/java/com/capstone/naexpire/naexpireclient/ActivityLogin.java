package com.capstone.naexpire.naexpireclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ActivityLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void clickLogin(View view){
        Intent intent = new Intent(this, ActivityNavDrawer.class);
        startActivity(intent);
    }

    public void clickForgot(View view){
        Toast.makeText(this, "Recovery Email Sent", Toast.LENGTH_SHORT).show();
    }

    public void clickRegister(View view){
        Intent intent = new Intent(this, ActivityRegUserInfo.class);
        startActivity(intent);
    }
}
