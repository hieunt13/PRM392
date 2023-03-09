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

    @Transaction
    @Insert(onConflict = REPLACE)
    public void addUsers(Users...user);

    @Transaction
    @Insert(onConflict = REPLACE)
    public void addAll(Users[] userList);

    @Query("SELECT * FROM users")
    public List<Users> getAll();

    @Query("SELECT * FROM users WHERE username = :username")
    public Users getUser(String username);

    @Transaction
    @Query("SELECT * FROM users")
    public List<UserWithInvoices> getUserWithInvoice();

    @Transaction
    @Insert
    public void insertAll(Users[] usersList);
}
