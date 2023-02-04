package com.example.labgame;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sbRacer1 = findViewById(R.id.sbRacer1);
        sbRacer2 = findViewById(R.id.sbRacer2);
        sbRacer3 = findViewById(R.id.sbRacer3);
        btnStart = findViewById(R.id.btnStart);
        btnReset = findViewById(R.id.btnReset);

        sbRacer1.setMax(finish);
        sbRacer2.setMax(finish);
        sbRacer3.setMax(finish);
    }

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
            default:
                return;

        }
    }

    private final Runnable startRace = new Runnable() {
        public void run() {

            //finished race and show message
            if(sbRacer1.getProgress() == finish || sbRacer2.getProgress() == finish || sbRacer2.getProgress() == finish ){
                handler.removeCallbacks(startRace);
                btnStart.setClickable(true);
                btnReset.setClickable(true);
                
                String message = null;
                if(sbRacer1.getProgress() == finish){
                    message = "Racer 1 win!";
                }
                if(sbRacer2.getProgress() == finish){
                    message = "Racer 2 win!";
                }
                if(sbRacer3.getProgress() == finish){
                    message = "Racer 3 win!";
                }
                Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
                return;
            }

            int upperbound = 15;
            Random rand1 = new Random();
            int int_random1 = rand1.nextInt(upperbound);

            Random rand2 = new Random();
            int int_random2 = rand1.nextInt(upperbound);

            Random rand3 = new Random();
            int int_random3 = rand1.nextInt(upperbound);

            // Updating progress bar
            sbRacer1.setProgress(sbRacer1.getProgress() + int_random1);
            sbRacer2.setProgress(sbRacer2.getProgress() + int_random2);
            sbRacer3.setProgress(sbRacer3.getProgress() + int_random3);

            // Running this thread after 100
            // milliseconds
            handler.postDelayed(this, 100);
        }
    };
}