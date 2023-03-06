package com.example.hotgearvn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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
        productList.add(new Product(R.drawable.laptopgaming,"Laptop Gaming Gigabyte G5 GE 51VN263SH","21,690,000₫",0));
        productList.add(new Product(R.drawable.laptopgaming,"Laptop Gaming Gigabyte G5 GE 51VN263SH","21,490,000₫",5));
        productList.add(new Product(R.drawable.laptopgaming,"Laptop Gaming Gigabyte G5 GE 51VN263SH","21,890,000₫",5));
        productList.add(new Product(R.drawable.laptopgaming,"Laptop Gaming Gigabyte G5 GE 51VN263SH","15,990,000₫",5));
        productList.add(new Product(R.drawable.laptopgaming,"Laptop Gaming Gigabyte G5 GE 51VN263SH","15,990,000₫",5));


        RecyclerViewProductAdapter adapter = new RecyclerViewProductAdapter(productList);
        rvProduct.setAdapter(adapter);
        rvProduct.setLayoutManager(new GridLayoutManager(this,2));
        rvProduct.addItemDecoration(new GridSpacingItemDecoration(2, 35, true));
    }
}