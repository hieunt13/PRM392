package com.example.hotgearvn.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.hotgearvn.entities.Invoice;
import com.example.hotgearvn.entities.Product;
import com.example.hotgearvn.entities.Product_Invoice;

import java.util.List;

public class InvoiceWithProducts {
    @Embedded public Invoice invoice;
    @Relation(parentColumn = "invoice_id",
            entityColumn = "product_id",
            associateBy = @Junction(Product_Invoice.class)
    )
    public List<Product> productList;
    @Relation(
            parentColumn = "invoice_id", // id of your Date clase
            entityColumn = "invoice_id"  // id of your DateSteakCrossRef class
    )
    public List<Product_Invoice> product_invoices_quantiy;
}
