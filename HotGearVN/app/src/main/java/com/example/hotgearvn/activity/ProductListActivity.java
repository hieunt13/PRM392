package com.example.hotgearvn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotgearvn.R;
import com.example.hotgearvn.adapter.RecyclerViewProductAdapter;
import com.example.hotgearvn.entities.Category;
import com.example.hotgearvn.entities.Product;
import com.example.hotgearvn.item.GridSpacingItemDecoration;
import com.example.hotgearvn.item.PaginationScrollListener;
import com.example.hotgearvn.utils.HandleEvent;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    ArrayList<Product> productList;

    RecyclerViewProductAdapter adapter;

    ArrayList<Product> productListByCategory;

    ArrayList<Product> resultProducts;

    RecyclerView rvProduct;

    ProgressBar progressBar;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = 1;


    private int productPerPage = 4;

    private int totalPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        resultProducts = new ArrayList<>();

        progressBar= findViewById(R.id.progress_bar);

        rvProduct = findViewById(R.id.rvProduct);

        productList = new ArrayList<>();
        productList.add(new Product(21690000.0, "Màn hình Philips 243V7QDSB 24\" IPS 75Hz", 100, R.drawable.laptopgaming, "Màn hình Philips 243V7QDSB 24\" IPS 75Hz", Long.valueOf("1")));
        productList.add(new Product(21690000.0, "Chuột Logitech G Pro X Superlight Wireless Red", 100, R.drawable.laptopgaming, "Chuột Logitech G Pro X Superlight Wireless Red", Long.valueOf("2")));
        productList.add(new Product(21690000.0, "Laptop Gaming Gigabyte G5 GE 51VN263SH", 100, R.drawable.laptopgaming, "Laptop Gaming Gigabyte G5 GE 51VN263SH", Long.valueOf("3")));
        productList.add(new Product(21690000.0, "Màn hình Philips 243V7QDSB 24\" IPS 75Hz", 100, R.drawable.laptopgaming, "Màn hình Philips 243V7QDSB 24\" IPS 75Hz", Long.valueOf("1")));
        productList.add(new Product(21690000.0, "Bàn phím cơ AKKO MOD007 PC Blue on White", 100, R.drawable.laptopgaming, "Bàn phím DAREU 1", Long.valueOf("4")));
        productList.add(new Product(21690000.0, "Bàn phím DAREU EK810X Black Grey Optical", 100, R.drawable.laptopgaming, "Bàn phím DAREU 2", Long.valueOf("4")));
        productList.add(new Product(21690000.0, "Bàn phím DAREU EK810X Black Grey Optical", 100, R.drawable.laptopgaming, "Bàn phím DAREU 3", Long.valueOf("4")));
        productList.add(new Product(21690000.0, "Bàn phím DAREU EK810X Black Grey Optical", 100, R.drawable.laptopgaming, "Bàn phím DAREU 4", Long.valueOf("4")));
        productList.add(new Product(21690000.0, "Bàn phím DAREU EK810X Black Grey Optical", 100, R.drawable.laptopgaming, "Bàn phím DAREU 5", Long.valueOf("4")));
        productList.add(new Product(21690000.0, "Bàn phím DAREU EK810X Black Grey Optical", 100, R.drawable.laptopgaming, "Bàn phím DAREU 6", Long.valueOf("4")));
        productList.add(new Product(21690000.0, "Bàn phím DAREU EK810X Black Grey Optical", 100, R.drawable.laptopgaming, "Bàn phím DAREU 7", Long.valueOf("4")));
        productList.add(new Product(21690000.0, "Bàn phím DAREU EK810X Black Grey Optical", 100, R.drawable.laptopgaming, "Bàn phím DAREU 8", Long.valueOf("4")));
        productList.add(new Product(21690000.0, "Bàn phím DAREU EK810X Black Grey Optical", 100, R.drawable.laptopgaming, "Bàn phím DAREU 9", Long.valueOf("4")));
        productList.add(new Product(21690000.0, "Bàn phím DAREU EK810X Black Grey Optical", 100, R.drawable.laptopgaming, "Bàn phím DAREU 10", Long.valueOf("4")));

        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        switch (category) {
            case "laptop":
                getProductListByCategory(3);
                getProductListByCategoryPerPage();
                listProductOnScreen();
                break;
            case "mouse":
                getProductListByCategory(2);
                getProductListByCategoryPerPage();
                listProductOnScreen();
                break;
            case "screen":
                getProductListByCategory(1);
                getProductListByCategoryPerPage();
                listProductOnScreen();
                break;
            case "keyboard":
                getProductListByCategory(4);
                getProductListByCategoryPerPage();
                listProductOnScreen();
                break;
        }

    }

    public void getProductListByCategory(int categoryId) {
        productListByCategory = new ArrayList<>();
        for (Product product : productList) {
            if (product.getCategoryId() == categoryId) productListByCategory.add(product);
        }
        totalPage = (int) (Math.ceil(productListByCategory.size() / productPerPage));
    }

    public void getProductListByCategoryPerPage() {
        int firstPageProduct;
        if (currentPage == 1) {
            firstPageProduct = 0;
        }else {
            firstPageProduct = (currentPage - 1) * productPerPage;
        }
        for (int i = firstPageProduct; i < firstPageProduct + productPerPage; i++) {
            if (i < productListByCategory.size()) {
                Product product = productListByCategory.get(i);
                resultProducts.add(product);
            }

        }
    }

    private void listProductOnScreen() {
        adapter = new RecyclerViewProductAdapter(resultProducts);
        rvProduct.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvProduct.setLayoutManager(gridLayoutManager);
        rvProduct.addItemDecoration(new GridSpacingItemDecoration(2, 35, true));
        if(productListByCategory.size()>productPerPage){
            rvProduct.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
                @Override
                public void loadMoreItems() {
                    if (currentPage == totalPage) {
                        isLastPage = true;
                    }
                    isLoading = true;
                    progressBar.setVisibility(View.VISIBLE);
                    currentPage += 1;
                    loadNextPage();
                }

                @Override
                public boolean isLoading() {
                    return isLoading;
                }

                @Override
                public boolean isLastPage() {
                    return isLastPage;
                }
            });
        }



    }

    private void loadNextPage() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                    getProductListByCategoryPerPage();
                    adapter.notifyDataSetChanged();
                    isLoading = false;
                progressBar.setVisibility(View.INVISIBLE);

            }
        },2000);


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