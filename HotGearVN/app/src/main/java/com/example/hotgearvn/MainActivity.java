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

import com.example.hotgearvn.dao.InvoiceDao;
import com.example.hotgearvn.dao.UsersDao;
import com.example.hotgearvn.database.HotGearDatabase;
import com.example.hotgearvn.entities.Invoice;
import com.example.hotgearvn.entities.Users;
import com.example.hotgearvn.executor.AppExecutors;
import com.example.hotgearvn.model.UserWithInvoices;

import org.apache.commons.io.IOUtils;

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

                                    }
                                });
                    }
                })
                .build();
        UsersDao usersDao = mDb.usersDao();
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
            List<UserWithInvoices> userWithInvoicesList = usersDao.getUserWithInvoice();
            int i = 0;
            for (UserWithInvoices o : userWithInvoicesList){
                for(Invoice object : o.invoiceList){
                    Log.d("AAA",object.toString());
                }
            }
            List<Users> users = usersDao.getAll();
            Log.d("asdas",users.toString());
        });

    }
}