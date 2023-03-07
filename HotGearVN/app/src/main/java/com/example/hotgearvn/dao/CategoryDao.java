package com.example.hotgearvn.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.hotgearvn.entities.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert(onConflict = REPLACE)
    void add(Category category);

    @Insert(onConflict = REPLACE)
    void addCategories(Category...categories);

    @Delete
    void delete(Category category);

    @Query("SELECT * FROM category")
    List<Category> getAll();

    @Query("SElECT * FROM category WHERE category_id = :id")
    Category getById(Long id);
}
