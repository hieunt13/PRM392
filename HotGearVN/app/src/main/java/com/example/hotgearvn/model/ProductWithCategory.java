package com.example.hotgearvn.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.hotgearvn.entities.Category;
import com.example.hotgearvn.entities.Product;

import java.util.List;

public class ProductWithCategory {
    @Embedded
    public Category category;
    @Relation(
            parentColumn = "category_id",
            entityColumn = "category_id"
    )
    public List<Product> productList;
}
