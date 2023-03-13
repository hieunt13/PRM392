package com.example.hotgearvn.activity;

import static com.example.hotgearvn.activity.LoginActivity.MYPREFERENCES;
import static com.example.hotgearvn.activity.LoginActivity.USERID;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotgearvn.R;
import com.example.hotgearvn.adapter.RecyclerViewCartAdapter;
import com.example.hotgearvn.dao.CategoryDao;
import com.example.hotgearvn.dao.ProductDao;
import com.example.hotgearvn.database.HotGearDatabase;
import com.example.hotgearvn.entities.Category;
import com.example.hotgearvn.entities.Product;
import com.example.hotgearvn.utils.HandleEvent;
import com.example.hotgearvn.utils.snakeBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ProductDetailActivity extends AppCompatActivity {
    ImageView proImg;
    TextView proCate;
    TextView proName;
    TextView proDetail;
    TextView proQuantity;
    TextView proPrice;
    Button btnAddToCart;
    Button btnBuy;
    SharedPreferences sharedpreferences;
    private ScrollView productDetailLayout;
    private String userIDCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Intent intent = getIntent();
        long productId = intent.getLongExtra("productId", 0);
        HotGearDatabase mDb = HotGearDatabase.getDatabase(this);
        ProductDao productDao = mDb.productDao();
        CategoryDao cateDao = mDb.categoryDao();
        Product product = productDao.getById(productId);
        Category cateProduct = cateDao.getById(product.getCategoryId());

        sharedpreferences = getSharedPreferences(MYPREFERENCES, MODE_PRIVATE);
        userIDCheck = sharedpreferences.getString(USERID, "");
        sharedpreferences = getSharedPreferences("ProductInCart", MODE_PRIVATE);

        proImg = findViewById(R.id.imageViewProductImage);
        proCate = findViewById(R.id.textViewProductCategory);
        proName = findViewById(R.id.textProductName);
        proDetail = findViewById(R.id.textViewProductDetail);
        proQuantity = findViewById(R.id.textProductQuantity);
        proPrice = findViewById(R.id.textProductPrice);
        btnAddToCart = findViewById(R.id.buttonAddToCartDetail);
        btnBuy = findViewById(R.id.buttonBuyDetail);
        productDetailLayout = findViewById(R.id.ProductDetailLayout);

        proImg.setImageResource(product.getImage());
        proCate.setText(cateProduct.getName());
        proName.setText(product.getName());
        proDetail.setText(product.getDetails());
        proQuantity.setText(product.getQuantity() + "");
        proPrice.setText(product.getPrice().intValue() + "đ");

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean added = false;
                makeSnakeBar(productDetailLayout, "Đã được thêm vào giỏ hàng", "Đi đến giỏ hàng", CartActivity.class);

                SharedPreferences.Editor editor = sharedpreferences.edit();
                Set<String> productCartList = sharedpreferences.getStringSet("productCart", new HashSet<String>());
                Set<String> productCartListTemp = new HashSet<String>();
                for (String cartProduct : productCartList) {
                    String[] productWithQuantity = cartProduct.split(",");
                    if(productWithQuantity[0].equals(product.getProductId())){
                        productCartListTemp.add(product.getProductId()+","+(Integer.valueOf(productWithQuantity[1])+1));
                        added = true;
                        productCartList.remove(cartProduct);
                        break;
                    }
                }
                if(!added){
                    productCartListTemp.add(product.getProductId()+","+1);
                }
                productCartListTemp.addAll(productCartList);
                editor.putStringSet("productCart", productCartListTemp);
                editor.commit();

//                Log.d("log", "id: " + listProductToCart);
//                if (listProductToCart.contains(String.valueOf(product.getProductId()))) {
//                    Intent intent = new Intent(view.getContext(), RecyclerViewCartAdapter.class);
//                    Intent intentGet = getIntent();
//                    int quantityProductInCart = intentGet.getIntExtra("quantityProductInCart", 1);
//                    quantityProductInCart++;
//                    intent.putExtra("quantityDuplicate", quantityProductInCart);
//                    intent.putExtra("priceInProductDetail",product.getPrice());
//                    Log.i("product quantity:", "" + quantityProductInCart);
//                }

            }
        });
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userIDCheck.equals("")) {
                    makeSnakeBar(productDetailLayout, "Bạn cần đăng nhập trước !", "Đăng nhập", LoginActivity.class);
                } else {
                    Set<String> productCartList = sharedpreferences.getStringSet("productCart", new HashSet<String>());
                    ArrayList<String> listOfProductId = new ArrayList<>();
                    for (String item : productCartList) {
                        listOfProductId.add(item);
                    }
                    Log.i("list", "list: " + listOfProductId);
                    Intent intent = new Intent(view.getContext(), PaymentActivity.class);
                    intent.putExtra("productIdToPay", listOfProductId);
                    view.getContext().startActivity(intent);
                }
            }
        });
    }

    public void makeSnakeBar(ViewGroup viewGroup, String msg, String actionMsg, Class<?> cls) {
        snakeBar.makeSnakeBarCommon(viewGroup, msg, actionMsg, cls);
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