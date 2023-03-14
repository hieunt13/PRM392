package com.example.hotgearvn.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.hotgearvn.entities.Product_Invoice;
import com.example.hotgearvn.model.InvoiceWithProducts;
import com.example.hotgearvn.model.ProductWithInvoices;

import java.util.List;

@Dao
public interface InvoiceProductDao {
    @Insert(onConflict = REPLACE)
    void addInvoiceProduct(Product_Invoice product_invoice);

    @Insert(onConflict = REPLACE)
    void addAllInvoiceProduct(Product_Invoice[] product_invoices);

    @Insert(onConflict = REPLACE)
    void addInvoiceProducts(Product_Invoice... product_invoice);

    @Query("SELECT * FROM invoice")
    List<InvoiceWithProducts> getInvoiceWithProducts();

    @Query("SELECT * FROM invoice WHERE invoice_id = :id")
    InvoiceWithProducts getInvoiceByIdWithProducts(Long id);

}
