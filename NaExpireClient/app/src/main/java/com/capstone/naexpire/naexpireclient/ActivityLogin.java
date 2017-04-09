package com.capstone.naexpire.naexpireclient;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ActivityLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void clickLogin(View view){
        //if first time logging in
        /*AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_set_foods, null);
        Button later = (Button) dialogView.findViewById(R.id.btnSetFoodsLater);
        Button yes = (Button) dialogView.findViewById(R.id.btnSetFoodsYes);

        dialogBuilder.setView(dialogView);
        final AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        later.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getBaseContext(), ActivityNavDrawer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ActivityRegFoodTypes.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                dialog.dismiss();
            }
        });*/

        //if not first time logging in
        Intent intent = new Intent(this, ActivityNavDrawer.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
