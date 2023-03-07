package com.example.hotgearvn.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.hotgearvn.item.GridSpacingItemDecoration;
import com.example.hotgearvn.R;
import com.example.hotgearvn.adapter.RecyclerViewProductAdapter;
import com.example.hotgearvn.entities.Product;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    ArrayList<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        RecyclerView rvProduct = findViewById(R.id.rvProduct);

        productList = new ArrayList<>();
        productList.add(new Product(21690000.0,"Laptop Gaming Gigabyte G5 GE 51VN263SH",100,R.drawable.laptopgaming,"Laptop Gaming Gigabyte G5 GE 51VN263SH", Long.valueOf("12")));
        productList.add(new Product(21690000.0,"Laptop Gaming Gigabyte G5 GE 51VN263SH",100,R.drawable.laptopgaming,"Laptop Gaming Gigabyte G5 GE 51VN263SH", Long.valueOf("12")));
        productList.add(new Product(21690000.0,"Laptop Gaming Gigabyte G5 GE 51VN263SH",100,R.drawable.laptopgaming,"Laptop Gaming Gigabyte G5 GE 51VN263SH", Long.valueOf("12")));
        productList.add(new Product(21690000.0,"Laptop Gaming Gigabyte G5 GE 51VN263SH",100,R.drawable.laptopgaming,"Laptop Gaming Gigabyte G5 GE 51VN263SH", Long.valueOf("12")));
        productList.add(new Product(21690000.0,"Laptop Gaming Gigabyte G5 GE 51VN263SH",100,R.drawable.laptopgaming,"Laptop Gaming Gigabyte G5 GE 51VN263SH", Long.valueOf("12")));
        productList.add(new Product(21690000.0,"Laptop Gaming Gigabyte G5 GE 51VN263SH",100,R.drawable.laptopgaming,"Laptop Gaming Gigabyte G5 GE 51VN263SH", Long.valueOf("12")));
        RecyclerViewProductAdapter adapter = new RecyclerViewProductAdapter(productList);
        rvProduct.setAdapter(adapter);
        rvProduct.setLayoutManager(new GridLayoutManager(this,2));
        rvProduct.addItemDecoration(new GridSpacingItemDecoration(2, 35, true));
    }
}