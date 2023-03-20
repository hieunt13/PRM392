package com.example.hotgearvn.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.hotgearvn.R;
import static com.example.hotgearvn.constants.MyPreferenceKey.*;

import com.example.hotgearvn.adapter.RecyclerviewInvoiceHistoryAdapter;
import com.example.hotgearvn.dao.InvoiceDao;
import com.example.hotgearvn.database.HotGearDatabase;
import com.example.hotgearvn.entities.Invoice;
import com.example.hotgearvn.item.DividerItemDecoration;
import com.example.hotgearvn.item.VerticalSpaceItemDecoration;
import com.example.hotgearvn.utils.HandleEvent;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class InvoiceHistoryActivity extends AppCompatActivity {
    RecyclerView rvInvoice;

    //Navigation
    DrawerLayout drawerLayout;
    NavigationView navView;

    ArrayList<Invoice> invoiceArrayList;

    SharedPreferences sharedpreferences;

    RecyclerviewInvoiceHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_history);
        //Navigation
        drawerLayout = findViewById(R.id.drawerLayout);
        navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);

        //Handle button login logout header
        Button btnLoginHeader;
        btnLoginHeader = findViewById(R.id.btnLogIn_LogOut);
        HandleEvent.buttonLoginLogoutEvent(btnLoginHeader,this);

        rvInvoice = findViewById(R.id.invoiceHistoryListView);
        HotGearDatabase mDb = HotGearDatabase.getDatabase(this);
        InvoiceDao invoiceDao = mDb.invoiceDao();
        sharedpreferences = getSharedPreferences(MYPREFERENCES,MODE_PRIVATE);
        String userid= sharedpreferences.getString(USERID,"");
        invoiceArrayList = new ArrayList<>();
        invoiceArrayList = (ArrayList<Invoice>) invoiceDao.getHistoryById(Long.parseLong(userid));

        adapter = new RecyclerviewInvoiceHistoryAdapter(invoiceArrayList, this);
        rvInvoice.setAdapter(adapter);
        rvInvoice.setLayoutManager(new LinearLayoutManager(this));
        rvInvoice.addItemDecoration(new VerticalSpaceItemDecoration(0));
        rvInvoice.addItemDecoration(new DividerItemDecoration(this));
//        rvInvoice.addItemDecoration(new DividerItemDecoration(this,R.drawable.divider));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void showPopUp(View v) {
        HandleEvent.showNavigation(this,navView,drawerLayout);
    }

    public void login_logout(View view) {
        HandleEvent.onClickLogin_Logout(view, this);
    }
}