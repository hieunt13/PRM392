package com.example.hotgearvn.utils;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

public class snakeBar {

    public static void makeSnakeBarCommon(ViewGroup viewGroup, String msg, String actionMsg, Class<?> cls) {
        Snackbar.make(viewGroup, msg, Snackbar.LENGTH_SHORT)
                .setAction(actionMsg, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), cls);
                        view.getContext().startActivity(intent);
                    }
                }).show();
    }
}
