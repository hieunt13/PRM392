package com.example.hotgearvn.data;

import com.example.hotgearvn.entities.Product_Invoice;

public class ProductInvoiceData {
    public static Product_Invoice[] populateProductInvoiceTable() {
        return new Product_Invoice[] {
                new Product_Invoice(Long.parseLong("1"),Long.parseLong("1"),1),
                new Product_Invoice(Long.parseLong("2"),Long.parseLong("2"),1),
                new Product_Invoice(Long.parseLong("3"),Long.parseLong("5"),1),
                new Product_Invoice(Long.parseLong("4"),Long.parseLong("5"),1),
                new Product_Invoice(Long.parseLong("5"),Long.parseLong("9"),1),
                new Product_Invoice(Long.parseLong("6"),Long.parseLong("12"),1),
                new Product_Invoice(Long.parseLong("7"),Long.parseLong("17"),1),
                new Product_Invoice(Long.parseLong("8"),Long.parseLong("18"),1),
                new Product_Invoice(Long.parseLong("9"),Long.parseLong("1"),1),
                new Product_Invoice(Long.parseLong("10"),Long.parseLong("20"),1),
                new Product_Invoice(Long.parseLong("11"),Long.parseLong("19"),1),
                new Product_Invoice(Long.parseLong("12"),Long.parseLong("4"),1),
                new Product_Invoice(Long.parseLong("13"),Long.parseLong("13"),1),
                new Product_Invoice(Long.parseLong("14"),Long.parseLong("17"),1),
                new Product_Invoice(Long.parseLong("15"),Long.parseLong("20"),1),
                new Product_Invoice(Long.parseLong("16"),Long.parseLong("7"),1),
        };
    }
}
