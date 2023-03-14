package com.example.hotgearvn.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys = {"invoice_id", "product_id"},
        foreignKeys = {
                @ForeignKey(
                        entity = Invoice.class,
                        parentColumns = {"invoice_id"},
                        childColumns = {"invoice_id"},
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Product.class,
                        parentColumns = {"product_id"},
                        childColumns = {"product_id"},
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                )
        }
)
public class Product_Invoice {
    @NonNull
    @ColumnInfo(name = "invoice_id")
    private Long invoiceId;

    @NonNull
    @ColumnInfo(name = "product_id")
    private Long productId;

    @NonNull
    @ColumnInfo(name = "productQuantityInvoice")
    private int productQuantityInvoice;

    public Product_Invoice(Long invoiceId, Long productId, int productQuantityInvoice) {
        this.invoiceId = invoiceId;
        this.productId = productId;
        this.productQuantityInvoice = productQuantityInvoice;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getProductQuantityInvoice() {
        return productQuantityInvoice;
    }

    public void setProductQuantityInvoice(int productQuantityInvoice) {
        this.productQuantityInvoice = productQuantityInvoice;
    }
}
