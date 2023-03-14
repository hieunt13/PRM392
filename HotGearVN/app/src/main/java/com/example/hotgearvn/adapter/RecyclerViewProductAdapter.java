package com.example.hotgearvn.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotgearvn.R;
import com.example.hotgearvn.activity.ProductDetailActivity;
import com.example.hotgearvn.entities.Product;
import com.example.hotgearvn.item.ItemClickListener;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewProductAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.productImg.setImageResource(product.getImage());
        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice().intValue()+" đ");
        if(product.getQuantity() != 0){
            holder.productQuantity.setText("Còn lại:"+product.getQuantity());
        }else{
            holder.productQuantity.setText("Hết hàng");
        }

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(view.getContext(), ProductDetailActivity.class);
                intent.putExtra("productId", product.getProductId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView productImg;
        TextView productName;
        TextView productPrice;

        TextView productQuantity;
        Button btnBuy;
        Button btnAddCart;

        private ItemClickListener itemClickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            productImg = itemView.findViewById(R.id.imageViewProduct);
            productName = itemView.findViewById(R.id.textViewName);
            productPrice = itemView.findViewById(R.id.textViewPrice);
            productQuantity = itemView.findViewById(R.id.textViewQuantity);
            btnBuy = itemView.findViewById(R.id.buttonBuyMain);
            btnAddCart = itemView.findViewById(R.id.buttonAddToCartMain);

        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        public void onClick(View v){
            itemClickListener.onClick(v, getAdapterPosition(),false);
        }

    }
}
