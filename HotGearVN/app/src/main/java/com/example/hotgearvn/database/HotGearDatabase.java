package com.example.hotgearvn.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.hotgearvn.dao.CategoryDao;
import com.example.hotgearvn.dao.InvoiceDao;
import com.example.hotgearvn.dao.InvoiceProductDao;
import com.example.hotgearvn.dao.ProductDao;
import com.example.hotgearvn.dao.UsersDao;
import com.example.hotgearvn.entities.Category;
import com.example.hotgearvn.entities.Invoice;
import com.example.hotgearvn.entities.Product;
import com.example.hotgearvn.entities.Product_Invoice;
import com.example.hotgearvn.entities.Users;

@Database(entities = {Users.class, Invoice.class, Category.class, Product.class, Product_Invoice.class},version = 1)
public abstract class HotGearDatabase extends RoomDatabase {
    public abstract UsersDao usersDao();
    public abstract InvoiceDao invoiceDao();
    public abstract CategoryDao categoryDao();
    public abstract ProductDao productDao();
    public abstract InvoiceProductDao invoiceProductDao();
}
