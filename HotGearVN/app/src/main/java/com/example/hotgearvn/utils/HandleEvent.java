package com.example.hotgearvn.utils;

import static com.example.hotgearvn.R.id;
import static com.example.hotgearvn.R.menu;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;

import com.example.hotgearvn.activity.CartActivity;
import com.example.hotgearvn.activity.LoginActivity;
import com.example.hotgearvn.MainActivity;
import com.example.hotgearvn.activity.PaymentActivity;
import com.example.hotgearvn.activity.ProductListActivity;

public class HandleEvent {
    public static void showPopUp(View v, Context context) {
        Log.d("Header","onClick");
        PopupMenu popupMenu = new PopupMenu(context, v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        popupMenu.setOnMenuItemClickListener(item -> {
            Intent intent = null;
            switch (item.getItemId()) {
                case id.mainPage:
                    intent = new Intent(context, MainActivity.class);
                    break;
                case id.laptop:
                    intent = new Intent(context, ProductListActivity.class);
                    intent.putExtra("category","laptop");
                    break;
                case id.mouse:
                    intent = new Intent(context, ProductListActivity.class);
                    intent.putExtra("category","mouse");
                    break;
                case id.screen:
                    intent = new Intent(context, ProductListActivity.class);
                    intent.putExtra("category","screen");
                    break;
                case id.keyboard:
                    intent = new Intent(context, ProductListActivity.class);
                    intent.putExtra("category","keyboard");
                    break;
                case id.cartPage:
                    intent = new Intent(context, CartActivity.class);
                    break;
                case id.profilePage:
                    intent = new Intent(context, LoginActivity.class);
                    break;
                case id.invoicePage:
                    intent = new Intent(context, PaymentActivity.class);
                    break;
                default:
                    return false;
            }
            context.startActivity(intent);
            return false;
        });
        inflater.inflate(menu.navigation, popupMenu.getMenu());
        popupMenu.show();
    }
}
