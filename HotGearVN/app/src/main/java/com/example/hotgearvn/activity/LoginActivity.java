package com.example.hotgearvn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotgearvn.R;
import com.example.hotgearvn.utils.HandleEvent;

public class LoginActivity extends AppCompatActivity {

    TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvSignUp = findViewById(R.id.tvSignUp);
        tvSignUp.setOnClickListener(view->{
            Intent intent = new Intent(this,RegisterActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void showPopUp(View v) {
        HandleEvent.showPopUp(v, this);
    }

    public void login_logout(View view) {
        HandleEvent.onClickLogin_Logout(view, this);
    }
}