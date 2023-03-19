package com.example.hotgearvn.activity;


import static com.example.hotgearvn.constants.MyPreferenceKey.*;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotgearvn.R;
import com.example.hotgearvn.adapter.RecyclerViewCartAdapter;
import com.example.hotgearvn.dao.ProductDao;
import com.example.hotgearvn.database.HotGearDatabase;
import com.example.hotgearvn.entities.Product;
import com.example.hotgearvn.utils.HandleEvent;
import com.example.hotgearvn.utils.snakeBar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CartActivity extends AppCompatActivity {
    ArrayList<Product> productsInCart;
    RecyclerView rvCart;
    RecyclerViewCartAdapter adapter;
    TextView tvNoProductInCart, tvTotalPriceCart, tvBuyProductInCart, delete, tvUpdateProductInCart;
    SharedPreferences sharedpreferences;
    Double totalPrice = 0.0;
    private ConstraintLayout cartLayout;

    //Navigation
    DrawerLayout drawerLayout;
    NavigationView navView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        //Navigation
        drawerLayout = findViewById(R.id.drawerLayout);
        navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);

        rvCart = findViewById(R.id.rvCart);
        tvNoProductInCart = findViewById(R.id.tvNoProductInCart);
        tvTotalPriceCart = findViewById(R.id.tvTotalPriceCart);
        delete = findViewById(R.id.tvDeleteProductInCart);
        tvBuyProductInCart = findViewById(R.id.tvBuyProductInCart);
        tvUpdateProductInCart = findViewById(R.id.tvUpdateProductInCart);
        cartLayout = findViewById(R.id.cartLayout);

        sharedpreferences = getSharedPreferences(MYPREFERENCES, MODE_PRIVATE);
        String userID = sharedpreferences.getString(USERID, "");
        sharedpreferences = getSharedPreferences(PRODUCTINCART, MODE_PRIVATE);
        Set<String> productCartListWithQuantity = sharedpreferences.getStringSet("productCart", new HashSet<String>());
        // Split id and quantity after get from cart
        Set<String> productCartListId = new HashSet<String>();
        Set<String> productCartListQuantity = new HashSet<String>();
        //Cast Set to List
        ArrayList<String> productCartListIdlist = new ArrayList<>(productCartListId);
        ArrayList<String> productCartListQuantitylist = new ArrayList<>(productCartListQuantity);
        for (String cartProduct : productCartListWithQuantity) {
            String[] productWithQuantity = cartProduct.split(",");
            productCartListIdlist.add(productWithQuantity[0]);
            productCartListQuantitylist.add(productWithQuantity[1]);
        }
        HotGearDatabase mDb = HotGearDatabase.getDatabase(this);
        ProductDao productDao = mDb.productDao();
        productsInCart = new ArrayList<>();

        for (int i = 0; i < productCartListIdlist.size(); i++) {
            Product product = productDao.getById(Long.valueOf(productCartListIdlist.get(i)));
            Double price = 0.0;
            price = product.getPrice() * Integer.parseInt(productCartListQuantitylist.get(i));
            totalPrice += price;
            productsInCart.add(product);
        }


        adapter = new RecyclerViewCartAdapter(productsInCart, this);

        if (productsInCart.size() == 0) {
            tvNoProductInCart.setText("Không có sản phẩm nào trong giỏ hàng");
        } else {
            tvNoProductInCart.setText("");
            tvTotalPriceCart.setText("Tổng thanh toán " + String.format("%,.0f",totalPrice) + "đ");
            rvCart.setAdapter(adapter);
            rvCart.setLayoutManager(new LinearLayoutManager(this));
        }


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
            }
        });
        tvUpdateProductInCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });

        tvBuyProductInCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userID.equals("")) {
                    makeSnakeBar(cartLayout, "Bạn cần đăng nhập trước!", "Đăng nhập", LoginActivity.class);
                } else {
                    Set<String> productCartList = sharedpreferences.getStringSet("productCart", new HashSet<String>());
                    ArrayList<String> listOfProductId = new ArrayList<>();
                    for (String item : productCartList) {
                        listOfProductId.add(item);
                    }
                    Log.i("list", "list: " + listOfProductId);
                    Intent intent = new Intent(view.getContext(), PaymentActivity.class);
                    intent.putExtra("productIdToPay", listOfProductId);
                    intent.putExtra("totalPriceInCart", totalPrice);
                    Log.i("cart when buy", "totalPrice: " + totalPrice);
                    view.getContext().startActivity(intent);
                }
            }
        });

        //Handle button login logout header
        Button btnLoginHeader;
        btnLoginHeader = findViewById(R.id.btnLogIn_LogOut);
        HandleEvent.buttonLoginLogoutEvent(btnLoginHeader, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void showPopUp(View v) {
        HandleEvent.showNavigation(this, navView,drawerLayout);
    }

    public void login_logout(View view) {
        HandleEvent.onClickLogin_Logout(view, this);
    }

    public void makeSnakeBar(ViewGroup viewGroup, String msg, String actionMsg, Class<?> cls) {
        snakeBar.makeSnakeBarCommon(viewGroup, msg, actionMsg, cls);
    }

    private void confirmDelete() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông Báo!");
        alert.setMessage("Bạn có muốn xóa không?");
        alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                recreate();
            }
        });
        alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }
}