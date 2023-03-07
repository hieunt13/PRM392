package com.example.hotgearvn.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.example.hotgearvn.R;
import com.example.hotgearvn.utils.HandleEvent;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void showPopUp(View v){
        HandleEvent.showPopUp(v,this);
    }
}