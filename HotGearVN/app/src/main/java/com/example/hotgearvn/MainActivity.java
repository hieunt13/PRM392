package com.example.hotgearvn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.database.SQLException;
import android.os.Build;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

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


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        HotGearDatabase.databaseWriteExecutor.execute(()->{
            List<Users> users = usersDao.getAll();
            List<Product> products = productDao.getAll();
            List<Category> categories = categoryDao.getAll();
            List<Invoice> invoices = invoiceDao.getAll();
            List<ProductWithInvoices> productInvoiceDataList = invoiceProductDao.getProductWithInvoices();
            Log.d("prducts", products.toString());
            Log.d("users",users.toString());
            Log.d("category",categories.toString());
            Log.d("invoice",invoices.toString());
            Log.d("productInvoice",productInvoiceDataList.toString());
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void showPopUp(View v){
        HandleEvent.showPopUp(v,this);

    }

    public void login_logout(View view){
        HandleEvent.onClickLogin_Logout(view,this);
    }

}