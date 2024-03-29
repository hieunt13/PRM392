package com.example.hotgearvn.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "invoice",foreignKeys ={
        @ForeignKey(
                entity = Users.class,
                parentColumns = {"user_id"},
                childColumns = {"user_id"},
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )
})
public class Invoice {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "invoice_id")
    private Long invoiceId;
    @ColumnInfo(name = "payment_method")
    private int paymentMethod;
    @ColumnInfo(name = "user_id",index = true)
    private Long userId;

    @ColumnInfo(name = "total_price")
    private double totalPrice;

    @ColumnInfo(name = "date")
    private String date;

    private String address;

//    public Invoice(int paymentMethod, Long userId, double totalPrice, String date) {
//        this.paymentMethod = paymentMethod;
//        this.userId = userId;
//        this.totalPrice = totalPrice;
//        this.date = date;
//    }

    public Invoice(int paymentMethod, Long userId, double totalPrice, String date, String address) {
        this.paymentMethod = paymentMethod;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.date = date;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId=" + invoiceId +
                ", paymentMethod=" + paymentMethod +
                ", userId=" + userId +
                ", totalPrice=" + totalPrice +
                ", date=" + date.toString() +
                '}';
    }
}
