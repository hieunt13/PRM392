package com.example.labgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    SeekBar sbRacer1;
    SeekBar sbRacer2;
    SeekBar sbRacer3;
    Button btnStart;
    Button btnReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sbRacer1 = findViewById(R.id.sbRacer1);
        sbRacer2 = findViewById(R.id.sbRacer2);
        sbRacer3 = findViewById(R.id.sbRacer3);
        btnStart = findViewById(R.id.btnStart);
        btnReset = findViewById(R.id.btnReset);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnStart:
                sbRacer1.setProgress(new Random(1).nextInt(50));
                sbRacer2.setProgress(new Random(1).nextInt(50));
                sbRacer3.setProgress(new Random(1).nextInt(50));
                break;
            case R.id.btnReset:
                sbRacer1.setProgress(0);
                sbRacer2.setProgress(0);
                sbRacer3.setProgress(0);
                break;
            default:
                return;

        }
    }
}