package com.example.hotgearvn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotgearvn.entities.Product;

import java.util.ArrayList;

public class RecyclerViewProductAdapter extends RecyclerView.Adapter<RecyclerViewProductAdapter.ViewHolder> {

    private ArrayList<Product> productList;

    public RecyclerViewProductAdapter(ArrayList<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public RecyclerViewProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.row_product, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewProductAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.productImg.setImageResource(product.getProductImg());
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(product.getProductPrice());
        if(product.getProductQuantity() != 0){
            holder.productQuantity.setText("Còn lại:"+product.getProductQuantity());
        }else{
            holder.productQuantity.setText("Hết hàng");
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView productImg;
        TextView productName;
        TextView productPrice;

        TextView productQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImg = itemView.findViewById(R.id.imageViewProduct);
            productName = itemView.findViewById(R.id.textViewName);
            productPrice = itemView.findViewById(R.id.textViewPrice);
            productQuantity = itemView.findViewById(R.id.textViewQuantity);

        }
    }
}
