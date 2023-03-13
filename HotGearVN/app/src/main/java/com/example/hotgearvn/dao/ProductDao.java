package com.example.hotgearvn.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.hotgearvn.entities.Category;
import com.example.hotgearvn.entities.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert(onConflict = REPLACE)
    void add(Product product);

    @Insert(onConflict = REPLACE)
    void addProducts(Product... products);

    @Delete
    void delete(Product product);

    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Query("SELECT * FROM product WHERE product_id = :id")
    Product getById(Long id);

    @Query("SELECT * FROM product WHERE category_id = :id")
    Product getByCategoryId(Long id);

    @Query("UPDATE Product SET quantity = :quantity WHERE product_id = :id")
    void updateQuantityById(int quantity, Long id);

}
