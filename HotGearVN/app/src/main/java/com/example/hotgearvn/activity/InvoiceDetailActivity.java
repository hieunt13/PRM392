package com.example.hotgearvn.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.hotgearvn.R;
import com.example.hotgearvn.adapter.ListViewProductInvoiceAdapter;
import com.example.hotgearvn.dao.InvoiceProductDao;
import com.example.hotgearvn.dao.ProductDao;
import com.example.hotgearvn.database.HotGearDatabase;
import com.example.hotgearvn.model.InvoiceWithProducts;

public class InvoiceDetailActivity extends AppCompatActivity {
    ListView lvInvoiceWithProduct;
    InvoiceWithProducts invoiceWithProducts;
    ListViewProductInvoiceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_detail);
        lvInvoiceWithProduct= findViewById(R.id.lvProduct);
        HotGearDatabase mDb = HotGearDatabase.getDatabase(this);
        InvoiceProductDao invoiceProductDao = mDb.invoiceProductDao();
        Intent intent = getIntent();
        long invoiceId = intent.getLongExtra("invoiceId",0);
        invoiceWithProducts = invoiceProductDao.getInvoiceByIdWithProducts(invoiceId);
        adapter = new ListViewProductInvoiceAdapter(this,R.layout.row_product_invoice,invoiceWithProducts);
        lvInvoiceWithProduct.setAdapter();
    }
}