package com.example.hotgearvn.data;

import com.example.hotgearvn.entities.Category;
import com.example.hotgearvn.entities.Users;

public class CategoryData {
    public static Category[] populateCategoryTable() {
        return new Category[] {
                new Category("Chuột"),
                new Category("PC"),
                new Category("Laptop"),
                new Category("Màn hình"),
                new Category("Bàn phím"),
        };
    }
}
