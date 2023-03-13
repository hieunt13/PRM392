package com.example.hotgearvn.activity;

import static com.example.hotgearvn.constants.MyPreferenceKey.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hotgearvn.R;
import com.example.hotgearvn.utils.HandleEvent;

public class ProfileActivity extends AppCompatActivity {
    TextView tvFullName;
    TextView tvEmail;
    TextView tvAddress;
    TextView tvPhone;
    TextView tvUsername;

    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Handle button login logout header
        Button btnLoginHeader;
        btnLoginHeader = findViewById(R.id.btnLogIn_LogOut);
        HandleEvent.buttonLoginLogoutEvent(btnLoginHeader,this);

        tvFullName = findViewById(R.id.tvFullName);
        tvEmail = findViewById(R.id.tvEmail);
        tvAddress = findViewById(R.id.tvAddress);
        tvPhone = findViewById(R.id.tvPhone);
        tvUsername = findViewById(R.id.tvUsername);

        pref = getApplicationContext().getSharedPreferences(MYPREFERENCES, 0);
        if(pref.getString(STATUS,"null").equalsIgnoreCase("login")){
            tvFullName.setText(pref.getString(FULLNAME,"Empty!"));
            tvUsername.setText(pref.getString(USERNAME,"Empty!"));
            tvEmail.setText(pref.getString(EMAIL,"Empty!"));
            tvAddress.setText(pref.getString(ADDRESS,"Empty!"));
            tvPhone.setText(pref.getString(PHONE,"Empty!"));
        }else {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }

    }

    public void showPopUp(View v){
        HandleEvent.showPopUp(v,this);
    }

    public void login_logout(View view){
        HandleEvent.onClickLogin_Logout(view,this);
    }
}