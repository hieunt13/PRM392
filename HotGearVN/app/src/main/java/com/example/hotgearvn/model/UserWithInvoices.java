package com.example.hotgearvn.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.hotgearvn.entities.Invoice;
import com.example.hotgearvn.entities.Users;

import java.util.List;

public class UserWithInvoices {
    @Embedded
    public Users users;
    @Relation(
            parentColumn = "user_id",
            entityColumn = "user_id"
    )
    public List<Invoice> invoiceList;
}
