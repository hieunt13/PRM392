package com.example.hotgearvn.activity;

import static com.example.hotgearvn.activity.LoginActivity.EMAIL;
import static com.example.hotgearvn.activity.LoginActivity.FULLNAME;
import static com.example.hotgearvn.activity.LoginActivity.MYPREFERENCES;
import static com.example.hotgearvn.activity.LoginActivity.PHONE;
import static com.example.hotgearvn.activity.LoginActivity.USERID;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.hotgearvn.MainActivity;
import com.example.hotgearvn.R;
import com.example.hotgearvn.dao.InvoiceDao;
import com.example.hotgearvn.dao.ProductDao;
import com.example.hotgearvn.database.HotGearDatabase;
import com.example.hotgearvn.entities.Invoice;
import com.example.hotgearvn.entities.Product;
import com.example.hotgearvn.utils.HandleEvent;
import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PaymentActivity extends AppCompatActivity {
    TextView tvViewCartProduct, etUserNamePayment, etEmailPayment, etPhonePayment, tvProductPricePayment, tvBackToCart;
    EditText etAddressPayment;
    Button btnCreateInvoice;
    Spinner spPayment;
    SharedPreferences sharedpreferences;

    private ConstraintLayout home;
    public static int methodPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        tvViewCartProduct = findViewById(R.id.tvViewCartProduct);
        etUserNamePayment = findViewById(R.id.etUserNamePayment);
        etEmailPayment = findViewById(R.id.etEmailPayment);
        etPhonePayment = findViewById(R.id.etPhonePayment);
        etAddressPayment = findViewById(R.id.etAddressPayment);
        tvProductPricePayment = findViewById(R.id.tvProductPricePayment);
        tvBackToCart = findViewById(R.id.tvBackToCart);
        btnCreateInvoice = findViewById(R.id.btnCreateInvoice);
        spPayment = findViewById(R.id.spPayment);

        sharedpreferences = getSharedPreferences(MYPREFERENCES, MODE_PRIVATE);
        Long userId = Long.valueOf(sharedpreferences.getString(USERID, ""));
        String fullname = sharedpreferences.getString(FULLNAME, "");
        String email = sharedpreferences.getString(EMAIL, "");
        String phone = sharedpreferences.getString(PHONE, "");
        ArrayList<String> listOfPaymentMethod = new ArrayList<String>();
        listOfPaymentMethod.add("Thanh toán khi giao hàng (COD)");
        listOfPaymentMethod.add("Thanh toán bằng Ngân hàng");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listOfPaymentMethod);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Intent intentGet = getIntent();
        int totalPrice = intentGet.getIntExtra("totalPriceInCart", 0);
        int priceFromProductDetail = intentGet.getIntExtra("priceInProductDetail", 0);

        double paymentPrice = totalPrice != 0 ? totalPrice : priceFromProductDetail;
        etUserNamePayment.setText(fullname);
        etEmailPayment.setText(email);
        etPhonePayment.setText(phone);
        spPayment.setAdapter(arrayAdapter);
        tvProductPricePayment.setText("" + paymentPrice + " đ");


        sharedpreferences = getSharedPreferences("ProductInCart", MODE_PRIVATE);
        Set<String> productCartList = sharedpreferences.getStringSet("productCart", new HashSet<String>());

        HotGearDatabase mDb = HotGearDatabase.getDatabase(this);
        ProductDao productDao = mDb.productDao();
        InvoiceDao invoiceDao = mDb.invoiceDao();
        ArrayList<Product> productList = (ArrayList<Product>) productDao.getAll();


        spPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(PaymentActivity.this, listOfPaymentMethod.get(i), Toast.LENGTH_SHORT).show();
                if (listOfPaymentMethod.get(i) == "1") {
                    openPaymentDialog();
                    methodPayment = i;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnCreateInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Product item : productList) {
                    if (productCartList.contains(String.valueOf(item.getProductId()))) {
                        int minusQuantity = item.getQuantity() - 1;
                        productDao.updateQuantityById(minusQuantity, item.getProductId());
                        invoiceDao.add(new Invoice(methodPayment, userId, paymentPrice));

                    }
                }
                paymentSuccess();
            }
        });

        tvBackToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        //Handle button login logout header
        Button btnLoginHeader;
        btnLoginHeader = findViewById(R.id.btnLogIn_LogOut);
        HandleEvent.buttonLoginLogoutEvent(btnLoginHeader,this);
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

    private void paymentSuccess() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setIcon(R.drawable.success);
        alert.setTitle("Thông Báo!");
        alert.setMessage("Đặt hàng thành công");
        alert.setPositiveButton("Quay về trang chủ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                startActivity(intent);
            }
        });
        alert.show();
    }

    private void openPaymentDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_payment_method_infor);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
    }
}