package com.example.hotgearvn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.hotgearvn.activity.ProductDetailActivity;
import com.example.hotgearvn.activity.ProductListActivity;
import com.example.hotgearvn.adapter.ImageSliderProductAdapter;
import com.example.hotgearvn.dao.CategoryDao;
import com.example.hotgearvn.dao.InvoiceDao;
import com.example.hotgearvn.dao.InvoiceProductDao;
import com.example.hotgearvn.dao.ProductDao;
import com.example.hotgearvn.dao.UsersDao;
import com.example.hotgearvn.database.HotGearDatabase;
import com.example.hotgearvn.entities.Product;
import com.example.hotgearvn.utils.HandleEvent;
import com.example.hotgearvn.utils.snakeBar;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
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
        //Handle button login logout header
        Button btnLoginHeader;
        btnLoginHeader = findViewById(R.id.btnLogIn_LogOut);
        HandleEvent.buttonLoginLogoutEvent(btnLoginHeader,this);

        mDb = HotGearDatabase.getDatabase(this);
        UsersDao usersDao = mDb.usersDao();
        ProductDao productDao = mDb.productDao();
        InvoiceDao invoiceDao = mDb.invoiceDao();
        CategoryDao categoryDao = mDb.categoryDao();
        InvoiceProductDao invoiceProductDao = mDb.invoiceProductDao();

        // image slider
        svProduct = findViewById(R.id.svProduct);
        sliderProducts.add(productDao.getById(Long.parseLong("10")));
        sliderProducts.add(productDao.getById(Long.parseLong("7")));
        sliderProducts.add(productDao.getById(Long.parseLong("8")));
        sliderProducts.add(productDao.getById(Long.parseLong("9")));
        ImageSliderProductAdapter imageSliderProductAdapter = new ImageSliderProductAdapter(this, sliderProducts);
        svProduct.setSliderAdapter(imageSliderProductAdapter);
        svProduct.startAutoCycle();

        //get all product
        List<Product> productList = productDao.getAll();

        productListDisplay("Mouse", 1, 2, productList);
        productListDisplay("Laptop", 3, 3, productList);
        productListDisplay("Screen", 4, 2, productList);
        
        // LOG OUT SECTION
        sharedpreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String saveInfo = sharedpreferences.getString("SaveinfoKey", "");
        Log.d("save", saveInfo);
    }

    private void productListDisplay(String categoryName,int categoryId, int displayQuantity, List<Product> productList) {
        List<Product> products = new ArrayList<>();
        int imageId = 0;
        int nameId = 0;
        int priceId = 0;
        int layoutId = 0;
        int viewMoreId = 0;
        int addToCartId = 0;
        int buyId = 0;
        TextView tvViewMore;
        TextView tvPriceProduct;
        TextView tvNameProduct;
        ImageView ivProduct;
        ConstraintLayout clsProduct;
        Button btnAddtoCart;
        Button btnBuy;
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
            addToCartId = getResources().getIdentifier("buttonAddToCart"+categoryName+"Main"+i, "id", getPackageName());
            buyId = getResources().getIdentifier("buttonBuy"+categoryName+"Main"+i, "id", getPackageName());
            tvPriceProduct = findViewById(priceId);
            tvNameProduct = findViewById(nameId);
            ivProduct = findViewById(imageId);
            clsProduct = findViewById(layoutId);
            btnAddtoCart = findViewById(addToCartId);
            btnBuy = findViewById(buyId);
            ivProduct.setImageResource(products.get(i-1).getImage());
            tvNameProduct.setText(products.get(i-1).getName());
            tvPriceProduct.setText(String.format("%,.0f",products.get(i-1).getPrice())+" đ");
            int position = i - 1;
            clsProduct.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
                intent.putExtra("productId", productsTemp.get(position).getProductId());
                v.getContext().startActivity(intent);
            });
            btnAddtoCart.setOnClickListener(v -> {

            });
            btnBuy.setOnClickListener(v -> {
                
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