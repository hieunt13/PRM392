package com.example.hotgearvn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.hotgearvn.dao.CategoryDao;
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
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        mDb = Room.databaseBuilder(getApplicationContext(),HotGearDatabase.class,"hotGear-database")
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        AppExecutors.getInstance().diskI0()
                                .execute(new Runnable() {
                                    @Override
                                    public void run()  {
                                        UsersDao usersDao = mDb.usersDao();
                                        usersDao.insertAll(UsersData.populateUsersTable());
                                        CategoryDao categoryDao = mDb.categoryDao();
                                        categoryDao.addAll(CategoryData.populateCategoryTable());
                                        ProductDao productDao = mDb.productDao();
                                        productDao.addProducts(ProductData.populateProductTable());
                                        InvoiceDao invoiceDao = mDb.invoiceDao();
                                        invoiceDao.addAll(InvoiceData.populateInvoiceTable());
                                        InvoiceProductDao invoiceProductDao = mDb.invoiceProductDao();
                                        invoiceProductDao.addInvoiceProducts(ProductInvoiceData.populateProductInvoiceTable());
                                    }
                                });
                    }
                })
                .build();
        UsersDao usersDao = mDb.usersDao();
        ProductDao productDao = mDb.productDao();
        InvoiceDao invoiceDao = mDb.invoiceDao();
        CategoryDao categoryDao = mDb.categoryDao();
        InvoiceProductDao invoiceProductDao = mDb.invoiceProductDao();
//        AppExecutors.getInstance().diskI0().execute(()->{
//
//            UsersDao usersDao = mDb.usersDao();
//            Users user = new Users("hieu","hieu","email","fullname","123123");
//            usersDao.add(user);
//            List<Users> users = usersDao.getAll();
//            Users user_insert = users.get(0);
//            Invoice invoice = new Invoice(0,user_insert.getUserId(),10000);
//            Invoice invoice1 = new Invoice(1,user_insert.getUserId(),2000);
//            Invoice invoice2 = new Invoice(2,user_insert.getUserId(),10100);
//            InvoiceDao invoiceDao = mDb.invoiceDao();
//            invoiceDao.addInvoices(invoice,invoice1,invoice2);
//
//            List<UserWithInvoices> userWithInvoicesList = usersDao.getUserWithInvoice();
//            int i = 0;
//            for (UserWithInvoices o : userWithInvoicesList){
//                for(Invoice object : o.invoiceList){
//                    Log.d("AAA",object.toString());
//                }
//            }
//
//        });
        AppExecutors.getInstance().diskI0().execute(()->{
//            List<UserWithInvoices> userWithInvoicesList = usersDao.getUserWithInvoice();
//            int i = 0;
//            for (UserWithInvoices o : userWithInvoicesList){
//                for(Invoice object : o.invoiceList){
//                    Log.d("AAA",object.toString());
//                }
//            }
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
}