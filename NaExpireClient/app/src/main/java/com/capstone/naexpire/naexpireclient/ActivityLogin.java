package com.capstone.naexpire.naexpireclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ActivityLogin extends AppCompatActivity {
    private SharedPreferences sharedPref;

    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPref = getSharedPreferences("com.capstone.naexpire.PREFERENCE_FILE_KEY",
                Context.MODE_PRIVATE);

        email = (EditText) findViewById(R.id.txtLoginEmail);
        password = (EditText) findViewById(R.id.txtLoginPassword);

        //if the user just came from registration set the email and password fields
        //with the credentials they entered while registering
        if(sharedPref.getInt("fromRegister", 0) == 1){
            email.setText(sharedPref.getString("email", ""));
            password.setText(sharedPref.getString("password", ""));

            //set that the user is not coming from registration, so the email and password fields
            //aren't pre-filled when the login activity is visited later
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("fromRegister", 2);
            editor.commit();
        }

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layLogin);

        layout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }
        });
    }

    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //user taps the login button
    public void clickLogin(View view){
        //gets the entered email and password into strings
        String enteredEmail = email.getText().toString();
        String enteredPass = password.getText().toString();

        //checks that both the email and password match the ones stored in shared preferences
        boolean match = enteredEmail.equals(sharedPref.getString("email", "")) &&
                enteredPass.equals(sharedPref.getString("password", ""));

        //if email and password are correct && both fields are filled
        if(match && !enteredEmail.isEmpty() && !enteredPass.isEmpty()){
            Intent intent = new Intent(this, ActivityNavDrawer.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent); //navigate to navigation drawer activity
        }
        else if(enteredEmail.isEmpty() || enteredPass.isEmpty()) //if either field is blank
            Toast.makeText(this, "Fill all fields.", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Incorrect email or password.", Toast.LENGTH_SHORT).show();
    }

    //user taps that they forgot their password
    public void clickForgot(View view){
        Toast.makeText(this, "Recovery email sent.", Toast.LENGTH_SHORT).show();
    }

    //user taps the register button
    public void clickRegister(View view){
        Intent intent = new Intent(this, ActivityRegUserInfo.class);
        startActivity(intent);
    }
}
