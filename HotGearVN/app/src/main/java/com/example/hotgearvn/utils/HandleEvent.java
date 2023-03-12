package com.example.hotgearvn.utils;

import static com.example.hotgearvn.R.id;
import static com.example.hotgearvn.R.menu;
import static com.example.hotgearvn.constants.MyPreferenceKey.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.example.hotgearvn.activity.CartActivity;
import com.example.hotgearvn.activity.LoginActivity;
import com.example.hotgearvn.MainActivity;
import com.example.hotgearvn.activity.PaymentActivity;
import com.example.hotgearvn.activity.ProductListActivity;
import com.example.hotgearvn.activity.ProfileActivity;
import com.example.hotgearvn.activity.RegisterActivity;

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
                case id.pc:
                    intent = new Intent(context, ProductListActivity.class);
                    intent.putExtra("category","pc");
                    break;
                case id.cartPage:
                    intent = new Intent(context, CartActivity.class);
                    break;
                case id.profilePage:
                    SharedPreferences pref = context.getApplicationContext().getSharedPreferences(MYPREFERENCES, 0);
                    if(pref.getString(STATUS,"null").equalsIgnoreCase("logout")){
                        intent = new Intent(context,LoginActivity.class);
                    }
                    intent = new Intent(context, ProfileActivity.class);
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

    public static void onClickLogin_Logout(View view,Context context){
        Intent intent = new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    public static void buttonLoginLogoutEvent(Button btnLoginLogout, Context context){
        //Hide button login when activity Login or Register started
        if (context.getClass() == LoginActivity.class || context.getClass() == RegisterActivity.class){
            btnLoginLogout.setVisibility(View.GONE);
        }else{
            btnLoginLogout.setVisibility(View.VISIBLE);
        }

        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(MYPREFERENCES, 0);
        if(preferences.getString(STATUS,"null").equalsIgnoreCase("login")){
            //turn login button into logout button and handle logout function
            btnLoginLogout.setText("Logout");
            String saveInfo = preferences.getString("SaveinfoKey", "");
            btnLoginLogout.setOnClickListener(v -> {
                if (saveInfo == "save") {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("StatusKey", "Logout");
                    editor.commit();
                } else {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();
                }
                //set back to login button
                btnLoginLogout.setText("Login");
                btnLoginLogout.setOnClickListener(view->{
                    onClickLogin_Logout(view,context);
                });
                Log.d("save", saveInfo);
            });
        }else{
            btnLoginLogout.setText("Login");
        }
    }
}
