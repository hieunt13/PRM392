package com.example.hotgearvn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotgearvn.R;
import com.example.hotgearvn.entities.Product;
import com.example.hotgearvn.model.InvoiceWithProducts;
import com.example.hotgearvn.model.ProductWithInvoices;

import java.util.ArrayList;

public class ListViewProductInvoiceAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private InvoiceWithProducts InvoiceWithProducts;

    public ListViewProductInvoiceAdapter(Context context, int layout, InvoiceWithProducts InvoiceWithProducts) {
        this.context = context;
        this.layout = layout;
        this.InvoiceWithProducts = InvoiceWithProducts;
    }

    @Override
    public int getCount() {
        return InvoiceWithProducts.productList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        ImageView imgProduct = view.findViewById(R.id.imageViewPro);
        TextView nameProduct = view.findViewById(R.id.textViewTenPro);
        TextView soProduct = view.findViewById(R.id.textViewSoluong);
        TextView giaProduct = view.findViewById(R.id.textViewGia);
        Product product = InvoiceWithProducts.productList.get(position);
        int quantity = InvoiceWithProducts.product_invoices_quantiy.get(position).getProductQuantityInvoice();
        imgProduct.setImageResource(product.getImage());
        soProduct.setText("x"+quantity);
        giaProduct.setText(product.getPrice().intValue()+"đ");
        return view;
    }
}
