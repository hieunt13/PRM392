package com.example.hotgearvn.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotgearvn.R;
import com.example.hotgearvn.adapter.RecyclerViewProductAdapter;
import com.example.hotgearvn.entities.Product;
import com.example.hotgearvn.item.GridSpacingItemDecoration;
import com.example.hotgearvn.utils.HandleEvent;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    ArrayList<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        RecyclerView rvProduct = findViewById(R.id.rvProduct);

        productList = new ArrayList<>();
        productList.add(new Product(21690000.0, "Laptop Gaming Gigabyte G5 GE 51VN263SH", 100, R.drawable.laptopgaming, "Laptop Gaming Gigabyte G5 GE 51VN263SH", Long.valueOf("12")));
        productList.add(new Product(21690000.0, "Laptop Gaming Gigabyte G5 GE 51VN263SH", 100, R.drawable.laptopgaming, "Laptop Gaming Gigabyte G5 GE 51VN263SH", Long.valueOf("12")));
        productList.add(new Product(21690000.0, "Laptop Gaming Gigabyte G5 GE 51VN263SH", 100, R.drawable.laptopgaming, "Laptop Gaming Gigabyte G5 GE 51VN263SH", Long.valueOf("12")));
        productList.add(new Product(21690000.0, "Laptop Gaming Gigabyte G5 GE 51VN263SH", 100, R.drawable.laptopgaming, "Laptop Gaming Gigabyte G5 GE 51VN263SH", Long.valueOf("12")));
        productList.add(new Product(21690000.0, "Laptop Gaming Gigabyte G5 GE 51VN263SH", 100, R.drawable.laptopgaming, "Laptop Gaming Gigabyte G5 GE 51VN263SH", Long.valueOf("12")));
        productList.add(new Product(21690000.0, "Laptop Gaming Gigabyte G5 GE 51VN263SH", 100, R.drawable.laptopgaming, "Laptop Gaming Gigabyte G5 GE 51VN263SH", Long.valueOf("12")));
        RecyclerViewProductAdapter adapter = new RecyclerViewProductAdapter(productList);
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