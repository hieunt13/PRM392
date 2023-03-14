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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotgearvn.R;
import com.example.hotgearvn.adapter.RecyclerViewCartAdapter;
import com.example.hotgearvn.dao.ProductDao;
import com.example.hotgearvn.database.HotGearDatabase;
import com.example.hotgearvn.entities.Product;
import com.example.hotgearvn.utils.HandleEvent;
import com.example.hotgearvn.utils.snakeBar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CartActivity extends AppCompatActivity {
    ArrayList<Product> productsInCart;
    RecyclerView rvCart;
    RecyclerViewCartAdapter adapter;
    TextView tvNoProductInCart, tvTotalPriceCart, tvBuyProductInCart, delete;
    SharedPreferences sharedpreferences;
    Double totalPrice = 0.0;
    int quantityProductInCart;
    private ConstraintLayout cartLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        rvCart = findViewById(R.id.rvCart);
        tvNoProductInCart = findViewById(R.id.tvNoProductInCart);
        tvTotalPriceCart = findViewById(R.id.tvTotalPriceCart);
        delete = findViewById(R.id.tvDeleteProductInCart);
        tvBuyProductInCart = findViewById(R.id.tvBuyProductInCart);
        cartLayout = findViewById(R.id.cartLayout);

        sharedpreferences = getSharedPreferences(MYPREFERENCES, MODE_PRIVATE);
        String userID = sharedpreferences.getString(USERID, "");
        sharedpreferences = getSharedPreferences("ProductInCart", MODE_PRIVATE);
        Set<String> productCartListWithQuantity = sharedpreferences.getStringSet("productCart", new HashSet<String>());
        Set<String> productCartList = new HashSet<>();
        for (String cartProduct : productCartListWithQuantity) {
            String[] productWithQuantity = cartProduct.split(",");
            productCartList.add(productWithQuantity[0]);
        }
        HotGearDatabase mDb = HotGearDatabase.getDatabase(this);
        ProductDao productDao = mDb.productDao();
        ArrayList<Product> productList = (ArrayList<Product>) productDao.getAll();
//        Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
        productsInCart = new ArrayList<>();
        for (Product item : productList) {
            if (productCartList.contains(String.valueOf(item.getProductId()))) {
                Log.i("cart", "id: " + item.getProductId());
                Product product = productDao.getById(item.getProductId());
                Double price = product.getPrice();
                Log.i("cart", "Price: " + price);
                totalPrice += price;
                Log.i("cart", "totalPrice: " + totalPrice);
//                intent.putExtra("totalPriceInCart", totalPrice);
                productsInCart.add(item);
            }
        }

        adapter = new RecyclerViewCartAdapter(productsInCart,this);

        if (productsInCart.size() == 0) {
            tvNoProductInCart.setText("Không có sản phẩm nào trong giỏ hàng");
        } else {
            tvNoProductInCart.setText("");
            tvTotalPriceCart.setText("Tổng thanh toán " + totalPrice.intValue() + "đ");
            rvCart.setAdapter(adapter);
            rvCart.setLayoutManager(new LinearLayoutManager(this));
        }


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDelete();
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
        HandleEvent.buttonLoginLogoutEvent(btnLoginHeader,this);
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