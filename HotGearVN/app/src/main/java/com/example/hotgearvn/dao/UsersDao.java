package com.example.hotgearvn.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.hotgearvn.entities.Users;
import com.example.hotgearvn.model.UserWithInvoices;

import java.util.List;

@Dao
public interface UsersDao {

    @Transaction
    @Insert(onConflict = REPLACE)
    public void add(Users user);

    @Query("SELECT * FROM users")
    public List<Users> getAll();

    @Transaction
    @Query("SELECT * FROM users")
    public List<UserWithInvoices> getUserWithInvoice();
}
