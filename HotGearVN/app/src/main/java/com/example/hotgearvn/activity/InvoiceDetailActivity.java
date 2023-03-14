package com.example.hotgearvn.activity;

import static com.example.hotgearvn.constants.MyPreferenceKey.MYPREFERENCES;
import static com.example.hotgearvn.constants.MyPreferenceKey.USERID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hotgearvn.R;
import com.example.hotgearvn.adapter.ListViewProductInvoiceAdapter;
import com.example.hotgearvn.dao.InvoiceProductDao;
import com.example.hotgearvn.dao.ProductDao;
import com.example.hotgearvn.database.HotGearDatabase;
import com.example.hotgearvn.entities.Users;
import com.example.hotgearvn.model.InvoiceWithProducts;
import com.example.hotgearvn.utils.HandleEvent;

public class InvoiceDetailActivity extends AppCompatActivity {
    ListView lvInvoiceWithProduct;
    InvoiceWithProducts invoiceWithProducts;
    ListViewProductInvoiceAdapter adapter;
    TextView tvTotalPrice;
    TextView tvPaymentMethod;
    TextView tvUserName;
    TextView tvUserPhone;
    TextView tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_detail);
        //Handle button login logout header
        Button btnLoginHeader;
        btnLoginHeader = findViewById(R.id.btnLogIn_LogOut);
        HandleEvent.buttonLoginLogoutEvent(btnLoginHeader,this);
        tvTotalPrice = findViewById(R.id.textViewTongTien);
        tvPaymentMethod = findViewById(R.id.textViewPttt);
        tvUserName = findViewById(R.id.textViewTen);
        tvUserPhone = findViewById(R.id.textViewSdt);
        tvAddress = findViewById(R.id.textViewDiaChi);
        lvInvoiceWithProduct= findViewById(R.id.lvProduct);
        HotGearDatabase mDb = HotGearDatabase.getDatabase(this);
        InvoiceProductDao invoiceProductDao = mDb.invoiceProductDao();
        Intent intent = getIntent();
        long invoiceId = intent.getLongExtra("invoiceId",0);
        invoiceWithProducts = invoiceProductDao.getInvoiceByIdWithProducts(invoiceId);
        adapter = new ListViewProductInvoiceAdapter(this,R.layout.row_product_invoice,invoiceWithProducts);
        lvInvoiceWithProduct.setAdapter(adapter);

        tvTotalPrice.setText(String.format("%,.0f", invoiceWithProducts.invoice.getTotalPrice())+"đ");
        tvPaymentMethod.setText(invoiceWithProducts.invoice.getPaymentMethod() == 0?"COD":"Thẻ ngân hàng");

        SharedPreferences sharedpreferences = getSharedPreferences(MYPREFERENCES,MODE_PRIVATE);
        String userid= sharedpreferences.getString(USERID,"");
        Users user = mDb.usersDao().getUserById(Long.parseLong(userid));
        tvUserName.setText(user.getFullName());
        tvUserPhone.setText(user.getPhone());
        if(user.getAddress() != null) tvAddress.setText(user.getAddress());
    }

    public void showPopUp(View v){
        HandleEvent.showPopUp(v,this);
    }

    public void login_logout(View view){
        HandleEvent.onClickLogin_Logout(view,this);
    }
}