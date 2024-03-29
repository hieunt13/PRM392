package com.example.hotgearvn.activity;

import static com.example.hotgearvn.constants.MyPreferenceKey.MYPREFERENCES;
import static com.example.hotgearvn.constants.MyPreferenceKey.USERID;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.hotgearvn.MainActivity;
import com.example.hotgearvn.R;
import com.example.hotgearvn.adapter.ListViewProductInvoiceAdapter;
import com.example.hotgearvn.dao.InvoiceProductDao;
import com.example.hotgearvn.database.HotGearDatabase;
import com.example.hotgearvn.entities.Invoice;
import com.example.hotgearvn.entities.Product_Invoice;
import com.example.hotgearvn.entities.Users;
import com.example.hotgearvn.model.InvoiceWithProducts;
import com.example.hotgearvn.utils.HandleEvent;
import com.google.android.material.navigation.NavigationView;

public class InvoiceDetailActivity extends AppCompatActivity {
    ListView lvInvoiceWithProduct;
    InvoiceWithProducts invoiceWithProducts;
    ListViewProductInvoiceAdapter adapter;
    TextView tvTotalPrice;
    TextView tvPaymentMethod;
    TextView tvUserName;
    TextView tvUserPhone;
    TextView tvAddress;
    Button btnHuy;
    //Navigation
    DrawerLayout drawerLayout;
    NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_detail);

        //Navigation
        drawerLayout = findViewById(R.id.drawerLayout);
        navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);

        //Handle button login logout header
        Button btnLoginHeader;
        btnLoginHeader = findViewById(R.id.btnLogIn_LogOut);
        HandleEvent.buttonLoginLogoutEvent(btnLoginHeader, this);

        btnHuy = findViewById(R.id.btnHuyDon);
        tvTotalPrice = findViewById(R.id.textViewTongTien);
        tvPaymentMethod = findViewById(R.id.textViewPttt);
        tvUserName = findViewById(R.id.textViewTen);
        tvUserPhone = findViewById(R.id.textViewSdt);
        tvAddress = findViewById(R.id.textViewDiaChi);
        lvInvoiceWithProduct = findViewById(R.id.lvProduct);
        HotGearDatabase mDb = HotGearDatabase.getDatabase(this);
        InvoiceProductDao invoiceProductDao = mDb.invoiceProductDao();
        Intent intent = getIntent();
        long invoiceId = intent.getLongExtra("invoiceId", 0);
        invoiceWithProducts = invoiceProductDao.getInvoiceByIdWithProducts(invoiceId);
        adapter = new ListViewProductInvoiceAdapter(this, R.layout.row_product_invoice, invoiceWithProducts);
        lvInvoiceWithProduct.setAdapter(adapter);

        tvTotalPrice.setText(String.format("%,.0f", invoiceWithProducts.invoice.getTotalPrice()) + "đ");
        tvPaymentMethod.setText(invoiceWithProducts.invoice.getPaymentMethod() == 0 ? "COD" : "Thẻ ngân hàng");

        SharedPreferences sharedpreferences = getSharedPreferences(MYPREFERENCES, MODE_PRIVATE);
        String userid = sharedpreferences.getString(USERID, "");
        Users user = mDb.usersDao().getUserById(Long.parseLong(userid));
        tvUserName.setText(user.getFullName());
        tvUserPhone.setText(user.getPhone());
        if (user.getAddress() != null) tvAddress.setText(user.getAddress());

        btnHuy.setOnClickListener(view->{
            confirmDelete();
        });
    }

    public void showPopUp(View v) {
        HandleEvent.showNavigation(this, navView, drawerLayout);
    }

    public void login_logout(View view) {
        HandleEvent.onClickLogin_Logout(view, this);
    }

    private void confirmDelete() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Thông Báo!");
        alert.setMessage("Bạn có muốn huỷ đơn hàng không?");
        alert.setPositiveButton("Có", (dialogInterface, i) -> {
            HotGearDatabase mDb = HotGearDatabase.getDatabase(this);
            mDb.invoiceDao().delete(invoiceWithProducts.invoice);
            for (Product_Invoice o: invoiceWithProducts.product_invoices_quantiy) {
                mDb.invoiceProductDao().delete(o);
            }
            Toast.makeText(InvoiceDetailActivity.this,"Đơn hàng đã huỷ!",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(InvoiceDetailActivity.this, MainActivity.class);
            startActivity(intent);
        });
        alert.setNegativeButton("Không", (dialogInterface, i) -> {
        });
        alert.show();
    }
}