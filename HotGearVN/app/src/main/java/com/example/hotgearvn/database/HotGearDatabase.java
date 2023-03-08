package com.example.hotgearvn.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.hotgearvn.dao.CategoryDao;
import com.example.hotgearvn.dao.InvoiceDao;
import com.example.hotgearvn.dao.InvoiceProductDao;
import com.example.hotgearvn.dao.ProductDao;
import com.example.hotgearvn.dao.UsersDao;
import com.example.hotgearvn.data.UsersData;
import com.example.hotgearvn.entities.Category;
import com.example.hotgearvn.entities.Invoice;
import com.example.hotgearvn.entities.Product;
import com.example.hotgearvn.entities.Product_Invoice;
import com.example.hotgearvn.entities.Users;
import com.example.hotgearvn.executor.AppExecutors;

@Database(entities = {Users.class, Invoice.class, Category.class, Product.class, Product_Invoice.class}, version = 1)
public abstract class HotGearDatabase extends RoomDatabase {
    private static volatile HotGearDatabase INSTANCE;

    public abstract UsersDao usersDao();

    public abstract InvoiceDao invoiceDao();

    public abstract CategoryDao categoryDao();

    public abstract ProductDao productDao();

    public abstract InvoiceProductDao invoiceProductDao();

    public static HotGearDatabase getDatabase(Context context) {
        INSTANCE =  Room.databaseBuilder(context.getApplicationContext(), HotGearDatabase.class, "hotGear-database")
                .addCallback(sRoomDatabaseCallback)
                .build();
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            AppExecutors.getInstance().diskI0().execute(() -> {
                        UsersDao usersDao = INSTANCE.usersDao();
                            usersDao.addAll(UsersData.populateUsersTable());
                    });
        }
    };

}
