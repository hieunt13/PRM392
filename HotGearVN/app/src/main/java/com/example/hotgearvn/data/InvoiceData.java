package com.example.hotgearvn.data;

import com.example.hotgearvn.entities.Invoice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class InvoiceData {

    public static Invoice[] populateInvoiceTable() {
        return new Invoice[] {
                new Invoice(0,Long.parseLong("1"),30000000,"22-11-2022","121 Tran Van Du P.13 , Q.Tan Binh"),
                new Invoice(0,Long.parseLong("1"),18000000,"20-12-2022","121 Tran Van Du P.13 , Q.Tan Binh"),
                new Invoice(0,Long.parseLong("1"),14000000,"15-02-2023","121 Tran Van Du P.13 , Q.Tan Binh"),
                new Invoice(0,Long.parseLong("2"),14000000,"16-12-2022","121 Tran Van Du P.13 , Q.Tan Binh"),
                new Invoice(0,Long.parseLong("2"),40000000,"14-06-2022","121 Tran Van Du P.13 , Q.Tan Binh"),
                new Invoice(1,Long.parseLong("2"),2200000,"11-11-2023","121 Tran Van Du P.13 , Q.Tan Binh"),
                new Invoice(1,Long.parseLong("3"),4700000,"25-11-2022","121 Tran Van Du P.13 , Q.Tan Binh"),
                new Invoice(1,Long.parseLong("3"),2450000,"31-12-2022","121 Tran Van Du P.13 , Q.Tan Binh"),
                new Invoice(1,Long.parseLong("3"),30000000,"01-01-2023","121 Tran Van Du P.13 , Q.Tan Binh"),
                new Invoice(1,Long.parseLong("3"),3500000,"05-02-2023","121 Tran Van Du P.13 , Q.Tan Binh"),
                new Invoice(0,Long.parseLong("4"),2500000,"20-11-2022","121 Tran Van Du P.13 , Q.Tan Binh"),
                new Invoice(0,Long.parseLong("4"),21500000,"20-12-2022","121 Tran Van Du P.13 , Q.Tan Binh"),
                new Invoice(1,Long.parseLong("4"),3100000,"31-12-2022","121 Tran Van Du P.13 , Q.Tan Binh"),
                new Invoice(0,Long.parseLong("5"),4700000,"20-11-2012","121 Tran Van Du P.13 , Q.Tan Binh"),
                new Invoice(1,Long.parseLong("5"),3500000,"20-12-2022","121 Tran Van Du P.13 , Q.Tan Binh"),
                new Invoice(0,Long.parseLong("5"),13000000,"21-01-2023","121 Tran Van Du P.13 , Q.Tan Binh"),

        };
    }
}
