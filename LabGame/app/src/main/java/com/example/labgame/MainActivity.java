package com.example.labgame;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final Handler handler = new Handler();
    private final int finish = 200;
    private int availableMoney = 0;
    SeekBar sbRacer1;
    SeekBar sbRacer2;
    SeekBar sbRacer3;
    Button btnStart;
    Button btnReset;
    EditText tvMoneyBet1;
    EditText tvMoneyBet2;
    EditText tvMoneyBet3;
    TextView tvMoney;
    CheckBox cbRacer1;
    CheckBox cbRacer2;
    CheckBox cbRacer3;

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(this.getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        sbRacer1 = findViewById(R.id.sbRacer1);
        sbRacer2 = findViewById(R.id.sbRacer2);
        sbRacer3 = findViewById(R.id.sbRacer3);
        btnStart = findViewById(R.id.btnStart);
        btnReset = findViewById(R.id.btnReset);
        tvMoneyBet1 = findViewById(R.id.tvMoneyBet1);
        tvMoneyBet2 = findViewById(R.id.tvMoneyBet2);
        tvMoneyBet3 = findViewById(R.id.tvMoneyBet3);
        tvMoney = findViewById(R.id.tvMoney);
        cbRacer1 = findViewById(R.id.cbRacer1);
        cbRacer2 = findViewById(R.id.cbRacer2);
        cbRacer3 = findViewById(R.id.cbRacer3);
        availableMoney = Integer.parseInt(tvMoney.getText().toString());
        disableEditText(tvMoneyBet1);
        disableEditText(tvMoneyBet2);
        disableEditText(tvMoneyBet3);

        //set seekBar max progress
        sbRacer1.setMax(finish);
        sbRacer2.setMax(finish);
        sbRacer3.setMax(finish);

        //set seekBar disable modify manually
        sbRacer1.setOnTouchListener((v, event) -> true);
        sbRacer2.setOnTouchListener((v, event) -> true);
        sbRacer3.setOnTouchListener((v, event) -> true);

        cbRacer1.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (!isChecked) {
                tvMoneyBet1.setText("");
                disableEditText(tvMoneyBet1);
                tvMoneyBet1.setError(null);
            } else {
                tvMoneyBet1.setText("");
                enableEditText(tvMoneyBet1);
            }
        });

        cbRacer2.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (!isChecked) {
                tvMoneyBet2.setText("");
                disableEditText(tvMoneyBet2);
                tvMoneyBet2.setError(null);
            } else {
                tvMoneyBet2.setText("");
                enableEditText(tvMoneyBet2);
            }
        });

        cbRacer3.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (!isChecked) {
                tvMoneyBet3.setText("");
                disableEditText(tvMoneyBet3);
                tvMoneyBet3.setError(null);
            } else {
                tvMoneyBet3.setText("");
                enableEditText(tvMoneyBet3);
            }
        });
    }

    //disable bet when not check
    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

    //enable bet when check
    private void enableEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
    }

    //validate empty,min bet
    private boolean minBetValidate(CheckBox cbRacer1, CheckBox cbRacer2, CheckBox cbRacer3) {
        boolean isValidAll = true;
        boolean cbRacsda = tvMoneyBet1.getText().toString().trim().isEmpty();
        boolean cbRacsd2a = cbRacer1.isChecked();
        if (cbRacer1.isChecked() && (tvMoneyBet1.getText().toString().trim().isEmpty() || Integer.parseInt(tvMoneyBet1.getText().toString()) < 10)) {
            tvMoneyBet1.setError("Min bet is 10");
            isValidAll = false;
        }
        if (cbRacer2.isChecked() && (tvMoneyBet2.getText().toString().trim().isEmpty() || Integer.parseInt(tvMoneyBet2.getText().toString()) < 10)) {
            tvMoneyBet2.setError("Min bet is 10");
            isValidAll = false;
        }
        if (cbRacer3.isChecked() && (tvMoneyBet3.getText().toString().trim().isEmpty() || Integer.parseInt(tvMoneyBet3.getText().toString()) < 10)) {
            tvMoneyBet3.setError("Min bet is 10");
            isValidAll = false;
        }
        return isValidAll;
    }

    //validate max bet
    private boolean maxBetValidate(CheckBox cbRacer1, CheckBox cbRacer2, CheckBox cbRacer3) {
        boolean isValid = true;
        int moneyBet1 = !cbRacer1.isChecked() || tvMoneyBet1.getText().toString().isEmpty() ? 0 : Integer.parseInt(tvMoneyBet1.getText().toString());
        int moneyBet2 = !cbRacer2.isChecked() || tvMoneyBet2.getText().toString().isEmpty() ? 0 : Integer.parseInt(tvMoneyBet2.getText().toString());
        int moneyBet3 = !cbRacer3.isChecked() || tvMoneyBet3.getText().toString().isEmpty() ? 0 : Integer.parseInt(tvMoneyBet3.getText().toString());
        int availableMoney = Integer.parseInt(tvMoney.getText().toString());
        int total = 0;
        int[] betArray = {moneyBet1, moneyBet2, moneyBet3};
        for (int i = 0; i < 3; i++) {
            total += (betArray[i] == -1 ? 0 : betArray[i]);
        }
        if (total > availableMoney) {
            Toast.makeText(getApplicationContext(), "Not enough money to bet. Please adjust your bet ", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }

    @SuppressLint("SetTextI18n")
    private void displayGameOverAlertDialog() {
        TextView title = new TextView(this);
        //Customise title
        title.setText("Game over!");
        title.setBackgroundColor(Color.BLACK);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCustomTitle(title);
        builder.setMessage("You run out of money!");
        builder.setPositiveButton("Play again", (dialog, which) -> {
            availableMoney += 100;
            tvMoney.setText(availableMoney + "");
            resetRace();
            dialog.dismiss();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        //style button to center
        Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
        layoutParams.weight = 5;
        btnPositive.setLayoutParams(layoutParams);
        btnNegative.setLayoutParams(layoutParams);
    }

    private String createMessage(String message, String racer) {
        if (message.isEmpty()) return racer;
        else return ", " + racer;
    }

    private final Runnable startRace = new Runnable() {
        @SuppressLint("SetTextI18n")
        public void run() {
            int profit = 0;
            int moneyBet1 = !cbRacer1.isChecked() || tvMoneyBet1.getText().toString().isEmpty() ? 0 : Integer.parseInt(tvMoneyBet1.getText().toString());
            int moneyBet2 = !cbRacer2.isChecked() || tvMoneyBet2.getText().toString().isEmpty() ? 0 : Integer.parseInt(tvMoneyBet2.getText().toString());
            int moneyBet3 = !cbRacer3.isChecked() || tvMoneyBet3.getText().toString().isEmpty() ? 0 : Integer.parseInt(tvMoneyBet3.getText().toString());

            //finished race and show message
            if (sbRacer1.getProgress() == finish || sbRacer2.getProgress() == finish || sbRacer3.getProgress() == finish) {
                btnReset.setClickable(true);
                String message = "";
                if (sbRacer1.getProgress() == finish) {
                    profit += moneyBet1 * 2;
                    message += createMessage(message, "Horse");
                }
                if (sbRacer2.getProgress() == finish) {
                    profit += moneyBet2 * 2;
                    message += createMessage(message, "Turtle");
                }
                if (sbRacer3.getProgress() == finish) {
                    profit += moneyBet3 * 2;
                    message += createMessage(message, "Rabbit");
                }
                availableMoney += profit;
                tvMoney.setText("" + availableMoney);
                if (availableMoney == 0) {
                    displayGameOverAlertDialog();
                }
                Toast.makeText(MainActivity.this, message + " win!", Toast.LENGTH_SHORT).show();
                handler.removeCallbacks(this);
            } else {
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
        }
    };

    @SuppressLint("SetTextI18n")
    private void startRace() {
        if (minBetValidate(cbRacer1, cbRacer2, cbRacer3) && maxBetValidate(cbRacer1, cbRacer2, cbRacer3)) {
            int moneyBet1 = !cbRacer1.isChecked() || tvMoneyBet1.getText().toString().isEmpty() ? 0 : Integer.parseInt(tvMoneyBet1.getText().toString());
            int moneyBet2 = !cbRacer2.isChecked() || tvMoneyBet2.getText().toString().isEmpty() ? 0 : Integer.parseInt(tvMoneyBet2.getText().toString());
            int moneyBet3 = !cbRacer3.isChecked() || tvMoneyBet3.getText().toString().isEmpty() ? 0 : Integer.parseInt(tvMoneyBet3.getText().toString());
            //check if user bet or not
            if (moneyBet1 != 0 || moneyBet2 != 0 || moneyBet3 != 0) {
                availableMoney -= (moneyBet1 + moneyBet2 + moneyBet3);
                tvMoney.setText(availableMoney + "");
                btnStart.setClickable(false);
                btnReset.setClickable(false);
                cbRacer1.setClickable(false);
                cbRacer2.setClickable(false);
                cbRacer3.setClickable(false);
                disableEditText(tvMoneyBet1);
                disableEditText(tvMoneyBet2);
                disableEditText(tvMoneyBet3);
                startRace.run();
            } else {
                Toast.makeText(this, "Please bet before starting the race!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void resetRace() {
        sbRacer1.setProgress(0);
        sbRacer2.setProgress(0);
        sbRacer3.setProgress(0);
        cbRacer1.setClickable(true);
        cbRacer2.setClickable(true);
        cbRacer3.setClickable(true);
        cbRacer1.setChecked(false);
        cbRacer2.setChecked(false);
        cbRacer3.setChecked(false);
        btnStart.setClickable(true);
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    public void onClick(View view) {
        if (availableMoney == 0) {
            displayGameOverAlertDialog();
        }
        switch (view.getId()) {
            case R.id.btnStart:
                startRace();
                break;
            case R.id.btnReset:
                resetRace();
                break;
        }
    }
}

