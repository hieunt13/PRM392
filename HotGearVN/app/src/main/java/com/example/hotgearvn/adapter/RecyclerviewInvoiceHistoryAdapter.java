package com.example.hotgearvn.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotgearvn.R;
import com.example.hotgearvn.activity.InvoiceDetailActivity;
import com.example.hotgearvn.activity.ProductDetailActivity;
import com.example.hotgearvn.dao.InvoiceProductDao;
import com.example.hotgearvn.database.HotGearDatabase;
import com.example.hotgearvn.entities.Invoice;
import com.example.hotgearvn.item.ItemClickListener;
import com.example.hotgearvn.model.InvoiceWithProducts;

import java.util.ArrayList;
import java.util.List;

public class RecyclerviewInvoiceHistoryAdapter extends RecyclerView.Adapter<RecyclerviewInvoiceHistoryAdapter.ViewHolder>{
    private ArrayList<Invoice> invoices;
    private Context context;

    public RecyclerviewInvoiceHistoryAdapter(ArrayList<Invoice> invoices, Context context) {
        this.invoices = invoices;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerviewInvoiceHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.row_invoice, parent, false);
        RecyclerviewInvoiceHistoryAdapter.ViewHolder viewHolder = new RecyclerviewInvoiceHistoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewInvoiceHistoryAdapter.ViewHolder holder, int position) {
        HotGearDatabase mDb = HotGearDatabase.getDatabase(context);
        InvoiceProductDao invoiceProductDao = mDb.invoiceProductDao();
        Invoice invoice = invoices.get(position);
        String title = " Đơn hàng "+(position+1)+" • "+invoice.getDate();
        holder.invoiceTitle.setText(title);
        int pttt = invoice.getPaymentMethod();
        holder.invoiceTotalPrice.setText("Tổng tiền: "+invoice.getTotalPrice()+"đ");
        if(pttt == 0){
            holder.invoiceTotalProduct.setText(" Phương thức thanh toán: COD");
        }else{
            holder.invoiceTotalProduct.setText(" Phương thức thanh toán: ngân hàng");
        }

        holder.invoiceImgIcon.setImageResource(R.drawable.arrow_right);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(view.getContext(), InvoiceDetailActivity.class);
                intent.putExtra("invoiceId", invoice.getInvoiceId());
                view.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return invoices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView invoiceTitle;
        TextView invoiceTotalPrice;

        TextView invoiceTotalProduct;
        ImageView invoiceImgIcon;


        private ItemClickListener itemClickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            invoiceTitle = itemView.findViewById(R.id.textViewInvoiceTitle);
            invoiceTotalPrice = itemView.findViewById(R.id.textViewTotalPrice);
            invoiceTotalProduct = itemView.findViewById(R.id.textViewTotalProduct);
            invoiceImgIcon = itemView.findViewById(R.id.imageViewIcon);
        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        public void onClick(View v){
            itemClickListener.onClick(v, getAdapterPosition(),false);
        }

    }
}
