package com.example.hotgearvn.activity;


import static com.example.hotgearvn.constants.MyPreferenceKey.EMAIL;
import static com.example.hotgearvn.constants.MyPreferenceKey.FULLNAME;
import static com.example.hotgearvn.constants.MyPreferenceKey.MYPREFERENCES;
import static com.example.hotgearvn.constants.MyPreferenceKey.PHONE;
import static com.example.hotgearvn.constants.MyPreferenceKey.PRODUCTINCART;
import static com.example.hotgearvn.constants.MyPreferenceKey.USERID;

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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hotgearvn.MainActivity;
import com.example.hotgearvn.R;
import com.example.hotgearvn.dao.InvoiceDao;
import com.example.hotgearvn.dao.InvoiceProductDao;
import com.example.hotgearvn.dao.ProductDao;
import com.example.hotgearvn.database.HotGearDatabase;
import com.example.hotgearvn.entities.Invoice;
import com.example.hotgearvn.entities.Product;
import com.example.hotgearvn.entities.Product_Invoice;
import com.example.hotgearvn.utils.HandleEvent;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class PaymentActivity extends AppCompatActivity {
    TextView tvViewCartProduct, etUserNamePayment, etEmailPayment, etPhonePayment, tvProductPricePayment, tvBackToCart;
    EditText etAddressPayment;
    Button btnCreateInvoice;
    Spinner spPayment;
    SharedPreferences sharedpreferences;
    long invoiceId;

    private ConstraintLayout home;
    int methodPayment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        String date = new Date(System.currentTimeMillis()).toString();
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
        Double totalPrice = intentGet.getDoubleExtra("totalPriceInCart", 10);
        etUserNamePayment.setText(fullname);
        etEmailPayment.setText(email);
        etPhonePayment.setText(phone);
        spPayment.setAdapter(arrayAdapter);
        tvProductPricePayment.setText("" + totalPrice.intValue() + " đ");


        sharedpreferences = getSharedPreferences(PRODUCTINCART, MODE_PRIVATE);
        Set<String> productCartList = sharedpreferences.getStringSet("productCart", new HashSet<String>());

        // split id and quantity after get from cart
        Set<String> productCartListId = new HashSet<String>();
        Set<String> productCartListQuantity = new HashSet<String>();
        for (String cartProduct : productCartList) {
            String[] productWithQuantity = cartProduct.split(",");
            //list id
            productCartListId.add(productWithQuantity[0]);
            //list quantity
            productCartListQuantity.add(productWithQuantity[1]);
        }

        HotGearDatabase mDb = HotGearDatabase.getDatabase(this);
        ProductDao productDao = mDb.productDao();
        InvoiceProductDao invoiceProductDao = mDb.invoiceProductDao();
        InvoiceDao invoiceDao = mDb.invoiceDao();
        ArrayList<Product> productList = (ArrayList<Product>) productDao.getAll();
        spPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(PaymentActivity.this, listOfPaymentMethod.get(i), Toast.LENGTH_SHORT).show();
                Log.i("methodPayment", listOfPaymentMethod.get(i) + ": " + methodPayment);
                if (listOfPaymentMethod.get(i) == "Thanh toán bằng Ngân hàng") {
                    openPaymentDialog();
                    methodPayment = i;
                    Log.i("methodPayment", listOfPaymentMethod.get(i) + ": " + methodPayment);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnCreateInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = etAddressPayment.getText().toString();
                sharedpreferences = getSharedPreferences(MYPREFERENCES, MODE_PRIVATE);
                String userID = sharedpreferences.getString(USERID, "");
                sharedpreferences = getSharedPreferences(PRODUCTINCART, MODE_PRIVATE);
                Set<String> productCartListWithQuantity = sharedpreferences.getStringSet("productCart", new HashSet<String>());
                // Split id and quantity after get from cart
                Set<String> productCartListId = new HashSet<String>();
                Set<String> productCartListQuantity = new HashSet<String>();
                //Cast Set to List
                ArrayList<String> productCartListIdlist = new ArrayList<>(productCartListId);
                ArrayList<String> productCartListQuantitylist = new ArrayList<>(productCartListQuantity);
                //Create new invoice
                invoiceDao.add(new Invoice(methodPayment, Long.valueOf(userID), totalPrice, date, address));
                Long newInvoiceID = Long.valueOf(invoiceDao.getAll().size());
                for (String cartProduct : productCartListWithQuantity) {
                    String[] productWithQuantity = cartProduct.split(",");
                    productCartListIdlist.add(productWithQuantity[0]);
                    productCartListQuantitylist.add(productWithQuantity[1]);
                }
                for (int i = 0; i < productCartListIdlist.size(); i++) {
                    Product product = productDao.getById(Long.valueOf(productCartListIdlist.get(i)));
                    int minusQuantity;
                    for (String itemQuantity : productCartListQuantity) {
                        minusQuantity = product.getQuantity() - Integer.parseInt(itemQuantity);
                        productDao.updateQuantityById(minusQuantity, product.getProductId());
                    }
                    // Create new invoice
                    invoiceProductDao.addInvoiceProduct(new Product_Invoice(newInvoiceID, product.getProductId(), Integer.valueOf(productCartListQuantitylist.get(i))));
                }
                //send notification
                send_notification(newInvoiceID);
                //Pop up dialog
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
        HandleEvent.buttonLoginLogoutEvent(btnLoginHeader, this);
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

        Button btnNo = dialog.findViewById(R.id.btnNo);
        Button btnYes = dialog.findViewById(R.id.btnYes);

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PaymentActivity.this, "Xác nhận thành công", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void send_notification(long invoiceId){
        RequestQueue mRequestQue = Volley.newRequestQueue(PaymentActivity.this);

        JSONObject json = new JSONObject();
        try {
            json.put("to", "/topics/" + "order_succeed");
            JSONObject notificationObj = new JSONObject();
            Log.d("invoiceId",String.valueOf(invoiceId));
            notificationObj.put("title", "Thông Báo!");
            notificationObj.put("message", "Đặt hàng thành công");
            notificationObj.put("invoiceId", Long.toString(invoiceId));
            //replace notification with data when went send data
            json.put("data", notificationObj);

            String URL = "https://fcm.googleapis.com/fcm/send";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    json,
                    response -> Log.d("MUR", "onResponse: "),
                    error -> Log.d("MUR", "onError: " + error.networkResponse)
            ) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=AAAALqfKzpE:APA91bEgUn2ujMWUBv5e0k9oGxyEIHGMejqmc0dKboYTKKnWUWuxpo7xUuiutMwurOIBJFieS0DyBGSwYwt2bxwj6HA4g5E0EPfSUTbz4llN2R0a7nOdVC8wXmm7FAkvQ2_SDqzfTc2D");
                    return header;
                }
            };


            mRequestQue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}