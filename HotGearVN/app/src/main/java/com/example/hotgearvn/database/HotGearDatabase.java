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
import com.example.hotgearvn.data.CategoryData;
import com.example.hotgearvn.data.InvoiceData;
import com.example.hotgearvn.data.ProductData;
import com.example.hotgearvn.data.ProductInvoiceData;
import com.example.hotgearvn.data.UsersData;
import com.example.hotgearvn.entities.Category;
import com.example.hotgearvn.entities.Invoice;
import com.example.hotgearvn.entities.Product;
import com.example.hotgearvn.entities.Product_Invoice;
import com.example.hotgearvn.entities.Users;
import com.example.hotgearvn.executor.AppExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Users.class, Invoice.class, Category.class, Product.class, Product_Invoice.class}, version = 1)
public abstract class HotGearDatabase extends RoomDatabase {
    private static volatile HotGearDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract UsersDao usersDao();

    public abstract InvoiceDao invoiceDao();

    public abstract CategoryDao categoryDao();

    public abstract ProductDao productDao();

    public abstract InvoiceProductDao invoiceProductDao();

    public static HotGearDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (HotGearDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    HotGearDatabase.class, "hotGear-database")
                            .addCallback(sRoomDatabaseCallback)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
                HotGearDatabase.databaseWriteExecutor.execute(()->{
                    UsersDao usersDao = INSTANCE.usersDao();
                    usersDao.insertAll(UsersData.populateUsersTable());
                    CategoryDao categoryDao = INSTANCE.categoryDao();
                    categoryDao.addAll(CategoryData.populateCategoryTable());
                    ProductDao productDao = INSTANCE.productDao();
                    productDao.addProducts(ProductData.populateProductTable());
                    InvoiceDao invoiceDao = INSTANCE.invoiceDao();
                    invoiceDao.addAll(InvoiceData.populateInvoiceTable());
                    InvoiceProductDao invoiceProductDao = INSTANCE.invoiceProductDao();
                    invoiceProductDao.addAllInvoiceProduct(ProductInvoiceData.populateProductInvoiceTable());
                });
        }
    };

}
