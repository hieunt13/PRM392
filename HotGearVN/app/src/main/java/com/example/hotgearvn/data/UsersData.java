package com.example.hotgearvn.data;

import com.example.hotgearvn.entities.Users;

public class UsersData {
    public static Users[] populateUsersTable() {
        return new Users[] {
                new Users("giavinh","123vinh","giavinh@gmail.com","Luu Gia Vinh","0987562123"),
                new Users("trunghieu","123hieu","trunghieu@gmail.com","Nguyen Trung Hieu","0123478965"),
                new Users("hongquang","123quang","hongquang@gmail.com","Pham Hong Quang","0912345678"),
                new Users("anhtu","123tu","anhtu@gmail.com","Ha Anh Tu","0987654321"),
                new Users("hunganh","123anh","hunganh@gmail.com","Pham Nguyen Hung Anh","0903647581")
        };
    }
}
