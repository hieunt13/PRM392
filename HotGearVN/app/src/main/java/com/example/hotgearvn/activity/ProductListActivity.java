package com.example.hotgearvn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.hotgearvn.R;
import com.example.hotgearvn.adapter.RecyclerViewProductAdapter;
import com.example.hotgearvn.dao.ProductDao;
import com.example.hotgearvn.database.HotGearDatabase;
import com.example.hotgearvn.entities.Category;
import com.example.hotgearvn.entities.Product;
import com.example.hotgearvn.executor.AppExecutors;
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

        //Handle button login logout header
        Button btnLoginHeader;
        btnLoginHeader = findViewById(R.id.btnLogIn_LogOut);
        HandleEvent.buttonLoginLogoutEvent(btnLoginHeader,this);

        resultProducts = new ArrayList<>();

        progressBar= findViewById(R.id.progress_bar);

        rvProduct = findViewById(R.id.rvProduct);

        productList = new ArrayList<>();
        HotGearDatabase mDb = HotGearDatabase.getDatabase(this);
        ProductDao productDao = mDb.productDao();
        productList = (ArrayList<Product>) productDao.getAll();
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        switch (category) {
            case "laptop":
                getProductListByCategory(3);
                getProductListByCategoryPerPage();
                listProductOnScreen();
                break;
            case "mouse":
                getProductListByCategory(1);
                getProductListByCategoryPerPage();
                listProductOnScreen();
                break;
            case "screen":
                getProductListByCategory(4);
                getProductListByCategoryPerPage();
                listProductOnScreen();
                break;
            case "keyboard":
                getProductListByCategory(5);
                getProductListByCategoryPerPage();
                listProductOnScreen();
                break;
            case "pc":
                getProductListByCategory(2);
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