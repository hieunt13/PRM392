package com.example.hotgearvn.data;

import com.example.hotgearvn.entities.Invoice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class InvoiceData {

    public static Invoice[] populateInvoiceTable() {
        return new Invoice[] {
                new Invoice(0,Long.parseLong("1"),30000000,"22-11-2022","44/2E Hau Lan 1B, Ba Diem, Hoc mon, Tp.HCM"),
                new Invoice(0,Long.parseLong("1"),18000000,"20-12-2022","44/2E Hau Lan 1B, Ba Diem, Hoc mon, Tp.HCM"),
                new Invoice(0,Long.parseLong("1"),14000000,"15-02-2023","VinHomes Grand Park Quan 9 Toa S1.02"),
                new Invoice(0,Long.parseLong("2"),14000000,"16-12-2022","44/2E Hau Lan 1B, Ba Diem, Hoc mon, Tp.HCM"),
                new Invoice(0,Long.parseLong("2"),40000000,"14-06-2022","VinHomes Grand Park Quan 9 Toa S1.02"),
                new Invoice(1,Long.parseLong("2"),2200000,"11-11-2023","VinHomes Grand Park Quan 9 Toa S1.03"),
                new Invoice(1,Long.parseLong("3"),4700000,"25-11-2022","44/2E Hau Lan 1B, Ba Diem, Hoc mon, Tp.HCM"),
                new Invoice(1,Long.parseLong("3"),2450000,"31-12-2022","VinHomes Grand Park Quan 9 Toa S1.02"),
                new Invoice(1,Long.parseLong("3"),30000000,"01-01-2023","VinHomes Grand Park Quan 9 Toa S1.02"),
                new Invoice(1,Long.parseLong("3"),3500000,"05-02-2023","123 Cong Hoa, Quan Tan Binh, Tp.HCM"),
                new Invoice(0,Long.parseLong("4"),2500000,"20-11-2022","VinHomes Grand Park Quan 9 Toa S1.03"),
                new Invoice(0,Long.parseLong("4"),21500000,"20-12-2022","123 Hau Lan 1B, Ba Diem, Hoc mon, Tp.HCM"),
                new Invoice(1,Long.parseLong("4"),3100000,"31-12-2022","VinHomes Grand Park Quan 9 Toa S2.02"),
                new Invoice(0,Long.parseLong("5"),4700000,"20-11-2012","VinHomes Grand Park Quan 9 Toa S3.02"),
                new Invoice(1,Long.parseLong("5"),3500000,"20-12-2022","VinHomes Grand Park Quan 9 Toa S1.03"),
                new Invoice(0,Long.parseLong("5"),13000000,"21-01-2023","123 Cong Hoa, Quan Tan Binh, Tp.HCM"),

        };
    }
}
