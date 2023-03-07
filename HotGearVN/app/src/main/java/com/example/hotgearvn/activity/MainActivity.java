package com.example.hotgearvn.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.hotgearvn.R;
import com.example.hotgearvn.dao.InvoiceDao;
import com.example.hotgearvn.dao.UsersDao;
import com.example.hotgearvn.database.HotGearDatabase;
import com.example.hotgearvn.entities.Invoice;
import com.example.hotgearvn.entities.Users;
import com.example.hotgearvn.executor.AppExecutors;
import com.example.hotgearvn.model.UserWithInvoices;
import com.example.hotgearvn.utils.HandleEvent;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private HotGearDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDb = Room.databaseBuilder(getApplicationContext(),HotGearDatabase.class,"hotGear-database").build();

        AppExecutors.getInstance().diskI0().execute(()->{

            UsersDao usersDao = mDb.usersDao();
            Users user = new Users("hieu","hieu","email","fullname","123123");
            usersDao.add(user);
            List<Users> users = usersDao.getAll();
            Users user_insert = users.get(0);
            Invoice invoice = new Invoice(0,user_insert.getUserId(),10000);
            Invoice invoice1 = new Invoice(1,user_insert.getUserId(),2000);
            Invoice invoice2 = new Invoice(2,user_insert.getUserId(),10100);
            InvoiceDao invoiceDao = mDb.invoiceDao();
            invoiceDao.addInvoices(invoice,invoice1,invoice2);

            List<UserWithInvoices> userWithInvoicesList = usersDao.getUserWithInvoice();
            int i = 0;

        });
        Log.d("asdas","asdasdsa");
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

}