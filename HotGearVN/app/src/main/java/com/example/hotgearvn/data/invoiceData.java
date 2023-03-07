package com.example.hotgearvn.data;

import com.example.hotgearvn.entities.Category;
import com.example.hotgearvn.entities.Invoice;

public class invoiceData {
    public static Invoice[] populateInvoiceTable() {
        return new Invoice[] {
                new Invoice(0,Long.parseLong("1"),30000000),
                new Invoice(0,Long.parseLong("1"),18000000),
                new Invoice(0,Long.parseLong("1"),14000000),
                new Invoice(0,Long.parseLong("2"),14000000),
                new Invoice(0,Long.parseLong("2"),40000000),
                new Invoice(1,Long.parseLong("2"),2200000),
                new Invoice(1,Long.parseLong("3"),4700000),
                new Invoice(1,Long.parseLong("3"),2450000),
                new Invoice(1,Long.parseLong("3"),30000000),
                new Invoice(1,Long.parseLong("3"),3500000),
                new Invoice(0,Long.parseLong("4"),2500000),
                new Invoice(0,Long.parseLong("4"),21500000),
                new Invoice(1,Long.parseLong("4"),3100000),
                new Invoice(0,Long.parseLong("5"),4700000),
                new Invoice(1,Long.parseLong("5"),3500000),
                new Invoice(0,Long.parseLong("5"),13000000),

        };
    }
}
