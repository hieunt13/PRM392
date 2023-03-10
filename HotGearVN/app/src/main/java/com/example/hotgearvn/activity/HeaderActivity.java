package com.example.hotgearvn.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.example.hotgearvn.R;

public class HeaderActivity extends AppCompatActivity {
    Button btnLoginLogout;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);
        btnLoginLogout = findViewById(R.id.btnLogIn_LogOut);
        sharedpreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String save = sharedpreferences.getString("SaveinfoKey", "");
        Log.d("luu", save);
    }


}