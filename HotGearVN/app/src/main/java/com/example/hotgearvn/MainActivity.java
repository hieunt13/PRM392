package com.example.hotgearvn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.SharedPreferences;
import android.database.SQLException;
import android.os.Build;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.hotgearvn.activity.ProductDetailActivity;
import com.example.hotgearvn.activity.ProductListActivity;
import com.example.hotgearvn.adapter.GridViewProductMainPageAdapter;
import com.example.hotgearvn.adapter.ImageSliderProductAdapter;
import com.example.hotgearvn.dao.CategoryDao;
import com.example.hotgearvn.R;
import com.example.hotgearvn.dao.InvoiceDao;
import com.example.hotgearvn.dao.InvoiceProductDao;
import com.example.hotgearvn.dao.ProductDao;
import com.example.hotgearvn.dao.UsersDao;
import com.example.hotgearvn.data.CategoryData;
import com.example.hotgearvn.data.InvoiceData;
import com.example.hotgearvn.data.ProductData;
import com.example.hotgearvn.data.ProductInvoiceData;
import com.example.hotgearvn.data.UsersData;
import com.example.hotgearvn.database.HotGearDatabase;
import com.example.hotgearvn.entities.Category;
import com.example.hotgearvn.entities.Invoice;
import com.example.hotgearvn.entities.Product;
import com.example.hotgearvn.entities.Users;
import com.example.hotgearvn.executor.AppExecutors;
import com.example.hotgearvn.model.ProductWithInvoices;
import com.example.hotgearvn.model.UserWithInvoices;
import com.example.hotgearvn.utils.HandleEvent;
import com.smarteist.autoimageslider.SliderView;


import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    Button btnLogout;
    SharedPreferences sharedpreferences;
    ArrayList<Product> sliderProducts = new ArrayList<>();
//  laptop
    List<Product> laptopProducts = new ArrayList<>();
    ImageView ivLaptop;
    TextView tvNameLaptop;
    TextView tvPriceLaptop;
    TextView tvViewMoreLaptop;
    ConstraintLayout cslLaptop;
//  screen
    List<Product> screenProducts = new ArrayList<>();
    ImageView ivScreen;
    TextView tvNameScreen;
    TextView tvPriceScreen;
    TextView tvViewMoreScreen;
    ConstraintLayout cslScreen;
//  mouse
    List<Product> mouseProducts = new ArrayList<>();
    ImageView ivMouse;
    TextView tvNameMouse;
    TextView tvPriceMouse;
    TextView tvViewMoreMouse;
    ConstraintLayout cslMouse;

    SliderView svProduct;

    GridView gvLaptopProduct;
    private HotGearDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDb = HotGearDatabase.getDatabase(this);
        UsersDao usersDao = mDb.usersDao();
        ProductDao productDao = mDb.productDao();
        InvoiceDao invoiceDao = mDb.invoiceDao();
        CategoryDao categoryDao = mDb.categoryDao();
        InvoiceProductDao invoiceProductDao = mDb.invoiceProductDao();

//        HotGearDatabase.databaseWriteExecutor.execute(() -> {
//            List<Users> users = usersDao.getAll();
//            List<Product> products = productDao.getAll();
//            List<Category> categories = categoryDao.getAll();
//            List<Invoice> invoices = invoiceDao.getAll();
//            List<ProductWithInvoices> productInvoiceDataList = invoiceProductDao.getProductWithInvoices();
//            Log.d("prducts", products.toString());
//            Log.d("users",users.toString());
//            Log.d("category",categories.toString());
//            Log.d("invoice",invoices.toString());
//            Log.d("productInvoice",productInvoiceDataList.toString());
//        });
        // image slider
        svProduct = findViewById(R.id.svProduct);
        HotGearDatabase mDb = HotGearDatabase.getDatabase(this);
        sliderProducts.add(productDao.getById(Long.parseLong("10")));
        sliderProducts.add(productDao.getById(Long.parseLong("7")));
        sliderProducts.add(productDao.getById(Long.parseLong("8")));
        sliderProducts.add(productDao.getById(Long.parseLong("9")));
        ImageSliderProductAdapter imageSliderProductAdapter = new ImageSliderProductAdapter(this, sliderProducts);
        svProduct.setSliderAdapter(imageSliderProductAdapter);
        svProduct.startAutoCycle();

        //get all product
        List<Product> productList = productDao.getAll();

        //product detail
//        int imageId = 0;
//        int nameId = 0;
//        int priceId = 0;
//        int layoutId = 0;

        // grid layout laptop product
//        Predicate<Product> byLaptop = product -> product.getCategoryId() == 3;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            laptopProducts = productList.stream().filter(byLaptop).collect(Collectors.toList());
//        }
//        tvViewMoreLaptop = findViewById(R.id.textViewMoreLaptop);
//        tvViewMoreLaptop.setOnClickListener(view -> {
//            Intent intent = new Intent(this, ProductListActivity.class);
//            intent.putExtra("category","laptop");
//            startActivity(intent);
//        });
//        for(int i = 1; i <= 3; i++){
//            imageId = getResources().getIdentifier("imageViewLaptopMain"+i, "id", getPackageName());
//            ivLaptop = findViewById(imageId);
//            nameId = getResources().getIdentifier("textViewNameLaptopMain"+i, "id", getPackageName());
//            tvNameLaptop = findViewById(nameId);
//            priceId = getResources().getIdentifier("textViewPriceLaptopMain"+i, "id", getPackageName());
//            tvPriceLaptop = findViewById(priceId);
//            layoutId = getResources().getIdentifier("constraintLayoutLaptopMain"+i, "id", getPackageName());
//            cslLaptop = findViewById(layoutId);
//            ivLaptop.setImageResource(laptopProducts.get(i-1).getImage());
//            tvNameLaptop.setText(laptopProducts.get(i-1).getName());
//            tvPriceLaptop.setText(laptopProducts.get(i-1).getPrice().intValue()+" đ");
//            int position = i - 1;
//            cslLaptop.setOnClickListener(v -> {
//                Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
//                intent.putExtra("productId", laptopProducts.get(position).getProductId());
//                v.getContext().startActivity(intent);
//            });
//        }

        // grid layout screen product
//        Predicate<Product> byScreen = product -> product.getCategoryId() == 4;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            screenProducts = productList.stream().filter(byScreen).collect(Collectors.toList());
//        }
//        tvViewMoreScreen = findViewById(R.id.textViewMoreScreen);
//        tvViewMoreScreen.setOnClickListener(view -> {
//            Intent intent = new Intent(this, ProductListActivity.class);
//            intent.putExtra("category","screen");
//            startActivity(intent);
//        });
//        for(int i = 1; i <= 2; i++){
//            imageId = getResources().getIdentifier("imageViewScreenMain"+i, "id", getPackageName());
//            ivScreen = findViewById(imageId);
//            nameId = getResources().getIdentifier("textViewNameScreenMain"+i, "id", getPackageName());
//            tvNameScreen = findViewById(nameId);
//            priceId = getResources().getIdentifier("textViewPriceScreenMain"+i, "id", getPackageName());
//            tvPriceScreen = findViewById(priceId);
//            layoutId = getResources().getIdentifier("constraintLayoutScreenMain"+i, "id", getPackageName());
//            cslScreen = findViewById(layoutId);
//            ivScreen.setImageResource(screenProducts.get(i-1).getImage());
//            tvNameScreen.setText(screenProducts.get(i-1).getName());
//            tvPriceScreen.setText(screenProducts.get(i-1).getPrice().intValue()+" đ");
//            int position = i - 1;
//            cslScreen.setOnClickListener(v -> {
//                Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
//                intent.putExtra("productId", screenProducts.get(position).getProductId());
//                v.getContext().startActivity(intent);
//            });
//        }

        // grid layout mouse product
//        Predicate<Product> byMouse = product -> product.getCategoryId() == 1;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            mouseProducts = productList.stream().filter(byMouse).collect(Collectors.toList());
//        }
//        tvViewMoreMouse = findViewById(R.id.textViewMoreMouse);
//        tvViewMoreMouse.setOnClickListener(view -> {
//            Intent intent = new Intent(this, ProductListActivity.class);
//            intent.putExtra("category","mouse");
//            startActivity(intent);
//        });
//        for(int i = 1; i <= 2; i++){
//            imageId = getResources().getIdentifier("imageViewMouseMain"+i, "id", getPackageName());
//            ivMouse = findViewById(imageId);
//            nameId = getResources().getIdentifier("textViewNameMouseMain"+i, "id", getPackageName());
//            tvNameMouse = findViewById(nameId);
//            priceId = getResources().getIdentifier("textViewPriceMouseMain"+i, "id", getPackageName());
//            tvPriceMouse = findViewById(priceId);
//            layoutId = getResources().getIdentifier("constraintLayoutMouseMain"+i, "id", getPackageName());
//            cslMouse = findViewById(layoutId);
//            ivMouse.setImageResource(mouseProducts.get(i-1).getImage());
//            tvNameMouse.setText(mouseProducts.get(i-1).getName());
//            tvPriceMouse.setText(mouseProducts.get(i-1).getPrice().intValue()+" đ");
//            int position = i - 1;
//            cslMouse.setOnClickListener(v -> {
//                Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
//                intent.putExtra("productId", mouseProducts.get(position).getProductId());
//                v.getContext().startActivity(intent);
//            });
//        }
//        gvLaptopProduct = findViewById(R.id.gridViewLaptopProduct);
//        GridViewProductMainPageAdapter gridViewProductMainPageAdapter = new GridViewProductMainPageAdapter(this, R.layout.row_product_main_page, laptopProducts);
//        gvLaptopProduct.setAdapter(gridViewProductMainPageAdapter);
//        gvLaptopProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(view.getContext(), ProductDetailActivity.class);
//                intent.putExtra("productId", laptopProducts.get(i).getProductId());
//                view.getContext().startActivity(intent);
//            }
//        });

        productListDisplay("Mouse", 1, 2, productList);
        productListDisplay("Laptop", 3, 3, productList);
        productListDisplay("Screen", 4, 2, productList);
        
        // LOG OUT SECTION
        btnLogout = findViewById(R.id.btnLogout);
        sharedpreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String saveInfo = sharedpreferences.getString("SaveinfoKey", "");
        Log.d("save", saveInfo);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saveInfo == "save") {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("StatusKey", "Logout");
                    editor.commit();
                } else {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.clear();
                    editor.commit();
                }
            }
        });



    }

    private void productListDisplay(String categoryName,int categoryId, int displayQuantity, List<Product> productList) {
        List<Product> products = new ArrayList<>();
        int imageId = 0;
        int nameId = 0;
        int priceId = 0;
        int layoutId = 0;
        int viewMoreId = 0;
        TextView tvViewMore;
        TextView tvPriceProduct;
        TextView tvNameProduct;
        ImageView ivProduct;
        ConstraintLayout clsProduct;
        Predicate<Product> byCategory = product -> product.getCategoryId() == categoryId;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            products = productList.stream().filter(byCategory).collect(Collectors.toList());
        }
        viewMoreId = getResources().getIdentifier("textViewMore"+categoryName, "id", getPackageName());
        tvViewMore = findViewById(viewMoreId);
        tvViewMore.setOnClickListener(view -> {
            Intent intent = new Intent(this, ProductListActivity.class);
            intent.putExtra("category",categoryName.toLowerCase());
            startActivity(intent);
        });
        // create temp to use in lamda
        List<Product> productsTemp = products;
        for(int i = 1; i <= displayQuantity; i++){
            imageId = getResources().getIdentifier("imageView"+categoryName+"Main"+i, "id", getPackageName());
            nameId = getResources().getIdentifier("textViewName"+categoryName+"Main"+i, "id", getPackageName());
            priceId = getResources().getIdentifier("textViewPrice"+categoryName+"Main"+i, "id", getPackageName());
            layoutId = getResources().getIdentifier("constraintLayout"+categoryName+"Main"+i, "id", getPackageName());
            tvPriceProduct = findViewById(priceId);
            tvNameProduct = findViewById(nameId);
            ivProduct = findViewById(imageId);
            clsProduct = findViewById(layoutId);
            ivProduct.setImageResource(products.get(i-1).getImage());
            tvNameProduct.setText(products.get(i-1).getName());
            tvPriceProduct.setText(products.get(i-1).getPrice().intValue()+" đ");
            int position = i - 1;
            clsProduct.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
                intent.putExtra("productId", productsTemp.get(position).getProductId());
                v.getContext().startActivity(intent);
            });
        }
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