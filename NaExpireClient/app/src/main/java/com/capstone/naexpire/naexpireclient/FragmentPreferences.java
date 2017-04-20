package com.capstone.naexpire.naexpireclient;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FragmentPreferences extends Fragment {
    private SharedPreferences sharedPref;

    public FragmentPreferences() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_preferences, container, false);

        sharedPref = getActivity().getSharedPreferences("com.capstone.naexpire.PREFERENCE_FILE_KEY",
                Context.MODE_PRIVATE);

        FragmentPreferences.this.getActivity().setTitle("Preferences"); //set title

        final EditText username = (EditText) view.findViewById(R.id.txtPrefUsername);
        final EditText email = (EditText) view.findViewById(R.id.txtPrefEmail);
        final EditText phone = (EditText) view.findViewById(R.id.txtPrefPhone);
        final EditText password = (EditText) view.findViewById(R.id.txtPrefPassword);
        final EditText cPassword = (EditText) view.findViewById(R.id.txtPrefPassword2);
        Button foods = (Button) view.findViewById(R.id.btnPrefFoods);
        Button save = (Button) view.findViewById(R.id.btnPrefSave);

        username.setText(sharedPref.getString("username", ""));
        email.setText(sharedPref.getString("email", ""));
        phone.setText(sharedPref.getString("phone", ""));

        foods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);

                FragmentEditFoodTypes fragmentEditFoodTypes = new FragmentEditFoodTypes();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, fragmentEditFoodTypes).commit();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("username", username.getText().toString());
                editor.putString("email", email.getText().toString());
                editor.putString("phone", phone.getText().toString());
                if(isValidPassword(password.getText().toString())){
                    if(password.getText().toString().equals(cPassword.getText().toString()))
                        editor.putString("password", password.getText().toString());
                    else Toast.makeText(FragmentPreferences.this.getContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(FragmentPreferences.this.getContext(),
                        "Password must have at least:\n\t8 Characters\n\t1 Capital\n\t1 Number\n\t1 Special Character",
                        Toast.LENGTH_SHORT).show();
                editor.commit();

                //update restaurant name in the navigation drawer
                NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                View header = navigationView.getHeaderView(0);
                TextView detail = (TextView) header.findViewById(R.id.lblDrawerEmail);

                detail.setText(sharedPref.getString("email", ""));

                Toast.makeText(FragmentPreferences.this.getContext(), "Changes Saved", Toast.LENGTH_SHORT).show();
            }
        });

        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layPreferences);
        RelativeLayout layout2 = (RelativeLayout) view.findViewById(R.id.layPrefsScroll);

        layout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                hideKeyboard(view);
                return false;
            }
        });

        layout2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                hideKeyboard(view);
                //return false;
            }
        });

        return view;
    }

    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
