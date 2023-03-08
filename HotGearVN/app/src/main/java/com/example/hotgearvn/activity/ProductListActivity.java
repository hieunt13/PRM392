package com.example.hotgearvn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotgearvn.R;
import com.example.hotgearvn.adapter.RecyclerViewProductAdapter;
import com.example.hotgearvn.entities.Category;
import com.example.hotgearvn.entities.Product;
import com.example.hotgearvn.item.GridSpacingItemDecoration;
import com.example.hotgearvn.utils.HandleEvent;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    ArrayList<Product> productList;

    RecyclerView rvProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        rvProduct = findViewById(R.id.rvProduct);

        productList = new ArrayList<>();
        productList.add(new Product(21690000.0, "Màn hình Philips 243V7QDSB 24\" IPS 75Hz", 100, R.drawable.laptopgaming, "Màn hình Philips 243V7QDSB 24\" IPS 75Hz", Long.valueOf("1")));
        productList.add(new Product(21690000.0, "Chuột Logitech G Pro X Superlight Wireless Red", 100, R.drawable.laptopgaming, "Chuột Logitech G Pro X Superlight Wireless Red", Long.valueOf("2")));
        productList.add(new Product(21690000.0, "Laptop Gaming Gigabyte G5 GE 51VN263SH", 100, R.drawable.laptopgaming, "Laptop Gaming Gigabyte G5 GE 51VN263SH", Long.valueOf("3")));
        productList.add(new Product(21690000.0, "Màn hình Philips 243V7QDSB 24\" IPS 75Hz", 100, R.drawable.laptopgaming, "Màn hình Philips 243V7QDSB 24\" IPS 75Hz", Long.valueOf("1")));
        productList.add(new Product(21690000.0, "Bàn phím cơ AKKO MOD007 PC Blue on White", 100, R.drawable.laptopgaming, "Bàn phím cơ AKKO MOD007 PC Blue on White", Long.valueOf("4")));
        productList.add(new Product(21690000.0, "Bàn phím DAREU EK810X Black Grey Optical", 100, R.drawable.laptopgaming, "Bàn phím DAREU EK810X Black Grey Optical", Long.valueOf("4")));
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        switch (category) {
            case "laptop":
                getProductListByCategory(productList,3);
                break;
            case "mouse":
                getProductListByCategory(productList,2);
                break;
            case "screen":
                getProductListByCategory(productList,1);
                break;
            case "keyboard":
                getProductListByCategory(productList,4);
                break;
        }

    }

    public void getProductListByCategory(ArrayList<Product> productList, int categoryId) {
        ArrayList<Product> resultProducts = new ArrayList<>();
        for (Product product : productList) {
            if (product.getCategoryId() == categoryId) resultProducts.add(product);
        }
        RecyclerViewProductAdapter adapter = new RecyclerViewProductAdapter(resultProducts);
        rvProduct.setAdapter(adapter);
        rvProduct.setLayoutManager(new GridLayoutManager(this, 2));
        rvProduct.addItemDecoration(new GridSpacingItemDecoration(2, 35, true));

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
}