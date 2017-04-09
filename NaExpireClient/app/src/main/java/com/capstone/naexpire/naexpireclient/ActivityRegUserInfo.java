package com.capstone.naexpire.naexpireclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivityRegUserInfo extends AppCompatActivity {

    EditText firstName, lastName, email, password, confirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_user_info);

        setTitle("Register"); //set activity title

        firstName = (EditText) findViewById(R.id.txtRegUserFirst);
        lastName = (EditText) findViewById(R.id.txtRegUserLast);
        email = (EditText) findViewById(R.id.txtRegUserEmail);
        password = (EditText) findViewById(R.id.txtRegUserPassword);
        confirmPass = (EditText) findViewById(R.id.txtRegUserPassword2);
    }

    public void clickSubmit(View view){
        //checks that all fields are filled
        Boolean ready = !firstName.getText().toString().isEmpty() &&
                !lastName.getText().toString().isEmpty() &&
                !email.getText().toString().isEmpty() &&
                !password.getText().toString().isEmpty() &&
                !confirmPass.getText().toString().isEmpty();

        //checks that password is valid
        Boolean valid = isValidPassword(password.getText().toString());

        //checks that passwords match
        Boolean match = password.getText().toString().equals(confirmPass.getText().toString());

        if(ready && valid && match){
            Toast.makeText(this, "A verification link has been sent to your email", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, ActivityLogin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else if(!ready) Toast.makeText(this, "Fill All Fields", Toast.LENGTH_SHORT).show();
        else if(!valid) Toast.makeText(this, "Password must have at least:\n\t8 Characters\n\t1 Capital\n\t1 Number\n\t1 Special Character", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "passwords do not match", Toast.LENGTH_SHORT).show();
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        //needs a cap, number, special char, and 8+ chars total
        final String PASSWORD_PATTERN = "((?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,})";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}
