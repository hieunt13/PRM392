package com.example.hotgearvn.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.hotgearvn.entities.Invoice;
import com.example.hotgearvn.entities.Product;
import com.example.hotgearvn.entities.Product_Invoice;

import java.util.List;

public class ProductWithInvoices {
    @Embedded
    public Product product;
    @Relation(parentColumn = "product_id",
            entityColumn = "invoice_id",
            associateBy = @Junction(Product_Invoice.class)
    )
    public List<Invoice> invoiceList;

}
