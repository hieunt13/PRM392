package com.example.hotgearvn;

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
        mDb = HotGearDatabase.getDatabase(this);
        AppExecutors.getInstance().diskI0().execute(()->{
            Log.d("asdas",mDb.usersDao().getAll().toString());
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

}