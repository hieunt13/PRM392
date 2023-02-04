package com.example.labgame;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final Handler handler = new Handler();
    private final int finish = 200;
    SeekBar sbRacer1;
    SeekBar sbRacer2;
    SeekBar sbRacer3;
    Button btnStart;
    Button btnReset;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sbRacer1 = findViewById(R.id.sbRacer1);
        sbRacer2 = findViewById(R.id.sbRacer2);
        sbRacer3 = findViewById(R.id.sbRacer3);
        btnStart = findViewById(R.id.btnStart);
        btnReset = findViewById(R.id.btnReset);

        //set seekBar max progress and disable modify manually
        sbRacer1.setMax(finish);
        sbRacer1.setOnTouchListener((v, event) -> true);
        sbRacer2.setMax(finish);
        sbRacer2.setOnTouchListener((v, event) -> true);
        sbRacer3.setMax(finish);
        sbRacer3.setOnTouchListener((v, event) -> true);
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                btnStart.setClickable(false);
                btnReset.setClickable(false);
                startRace.run();
                break;
            case R.id.btnReset:
                sbRacer1.setProgress(0);
                sbRacer2.setProgress(0);
                sbRacer3.setProgress(0);
                break;
        }
    }

    private final Runnable startRace = new Runnable() {
        public void run() {

            //finished race and show message
            if (sbRacer1.getProgress() == finish || sbRacer2.getProgress() == finish || sbRacer2.getProgress() == finish) {
                handler.removeCallbacks(startRace);
                btnStart.setClickable(true);
                btnReset.setClickable(true);

                String message = null;
                if (sbRacer1.getProgress() == finish) {
                    message = "Racer 1 win!";
                }
                if (sbRacer2.getProgress() == finish) {
                    message = "Racer 2 win!";
                }
                if (sbRacer3.getProgress() == finish) {
                    message = "Racer 3 win!";
                }
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                return;
            }

            int upperbound = 15;
            Random rand = new Random();

            int racer1_speed = rand.nextInt(upperbound);
            int racer2_speed = rand.nextInt(upperbound);
            int racer3_speed = rand.nextInt(upperbound);

            // Updating progress bar
            sbRacer1.setProgress(sbRacer1.getProgress() + racer1_speed);
            sbRacer2.setProgress(sbRacer2.getProgress() + racer2_speed);
            sbRacer3.setProgress(sbRacer3.getProgress() + racer3_speed);

            // Running this thread after 100
            // milliseconds
            handler.postDelayed(this, 100);
        }
    };
}