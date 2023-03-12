package com.example.hotgearvn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotgearvn.R;
import com.example.hotgearvn.dao.CategoryDao;
import com.example.hotgearvn.dao.ProductDao;
import com.example.hotgearvn.database.HotGearDatabase;
import com.example.hotgearvn.entities.Category;
import com.example.hotgearvn.entities.Product;
import com.example.hotgearvn.utils.HandleEvent;

public class ProductDetailActivity extends AppCompatActivity {
    ImageView proImg;
    TextView proCate;
    TextView proName;
    TextView proDetail;
    TextView proQuantity;
    TextView proPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        //Handle button login logout header
        Button btnLoginHeader;
        btnLoginHeader = findViewById(R.id.btnLogIn_LogOut);
        HandleEvent.buttonLoginLogoutEvent(btnLoginHeader,this);

        Intent intent = getIntent();
        long productId = intent.getLongExtra("productId",0);
        HotGearDatabase mDb = HotGearDatabase.getDatabase(this);
        ProductDao productDao = mDb.productDao();
        CategoryDao cateDao = mDb.categoryDao();
        Product product = productDao.getById(productId);
        Category cateProduct = cateDao.getById(product.getCategoryId());

        proImg = findViewById(R.id.imageViewProductImage);
        proCate = findViewById(R.id.textViewProductCategory);
        proName = findViewById(R.id.textProductName);
        proDetail = findViewById(R.id.textViewProductDetail);
        proQuantity = findViewById(R.id.textProductQuantity);
        proPrice = findViewById(R.id.textProductPrice);

        proImg.setImageResource(product.getImage());
        proCate.setText(cateProduct.getName());
        proName.setText(product.getName());
        proDetail.setText(product.getDetails());
        proQuantity.setText(product.getQuantity()+"");
        proPrice.setText(product.getPrice().intValue()+"đ");

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