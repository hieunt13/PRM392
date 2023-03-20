package com.example.hotgearvn.adapter;

import static com.example.hotgearvn.constants.MyPreferenceKey.MYPREFERENCES;
import static com.example.hotgearvn.constants.MyPreferenceKey.USERID;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotgearvn.R;
import com.example.hotgearvn.activity.CartActivity;
import com.example.hotgearvn.activity.LoginActivity;
import com.example.hotgearvn.activity.ProductDetailActivity;
import com.example.hotgearvn.entities.Product;
import com.example.hotgearvn.item.ItemClickListener;
import com.example.hotgearvn.utils.snakeBar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
        holder.productPrice.setText(String.format("%,.0f",product.getPrice())+" đ");
        if(product.getQuantity() != 0){
            holder.productQuantity.setText("Còn lại: "+product.getQuantity());
        }else{
            holder.productQuantity.setText("Hết hàng!");
        }

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(view.getContext(), ProductDetailActivity.class);
                intent.putExtra("productId", product.getProductId());
                view.getContext().startActivity(intent);
            }
        });

        holder.btnAddCart.setOnClickListener(view -> {
            if( !holder.productQuantity.getText().toString().equalsIgnoreCase("Hết hàng!")){
                boolean added = false;
                Toast.makeText(view.getContext(), "Sản phẩm đã được thêm vào giỏi hàng", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedpreferences = view.getContext().getSharedPreferences("ProductInCart", view.getContext().MODE_PRIVATE);
                Set<String> productCartList = sharedpreferences.getStringSet("productCart", new HashSet<String>());
                Set<String> productCartListTemp = new HashSet<String>();
                for (String cartProduct : productCartList) {
                    String[] productWithQuantity = cartProduct.split(",");
                    if (productWithQuantity[0].equalsIgnoreCase(product.getProductId().toString())) {
                        productCartListTemp.add(product.getProductId() + "," + (Integer.valueOf(productWithQuantity[1]) + 1));
                        added = true;
                        productCartList.remove(cartProduct);
                        break;
                    }
                }
                if (!added) {
                    productCartListTemp.add(product.getProductId() + "," + 1);
                }
                productCartListTemp.addAll(productCartList);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putStringSet("productCart", productCartListTemp);
                editor.commit();
            }else{
                Toast.makeText(view.getContext(), "Sản phẩm hết hàng!",Toast.LENGTH_SHORT).show();
            }

        });
        holder.btnBuy.setOnClickListener(view -> {
            SharedPreferences sharedpreferences;
            sharedpreferences = view.getContext().getSharedPreferences(MYPREFERENCES, view.getContext().MODE_PRIVATE);
            String userIDCheck = sharedpreferences.getString(USERID, "");
            if (userIDCheck.equals("")) {
                Toast.makeText(view.getContext(), "Bạn cần đăng nhập trước!", Toast.LENGTH_SHORT).show();
            } else {
                boolean added = false;
                sharedpreferences = view.getContext().getSharedPreferences("ProductInCart", view.getContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                Set<String> productCartList = sharedpreferences.getStringSet("productCart", new HashSet<String>());
                Set<String> productCartListTemp = new HashSet<String>();
                for (String cartProduct : productCartList) {
                    String[] productWithQuantity = cartProduct.split(",");
                    if (productWithQuantity[0].equalsIgnoreCase(product.getProductId().toString())) {
                        productCartListTemp.add(product.getProductId() + "," + (Integer.valueOf(productWithQuantity[1]) + 1));
                        added = true;
                        productCartList.remove(cartProduct);
                        break;
                    }
                }
                if (!added) {
                    productCartListTemp.add(product.getProductId() + "," + 1);
                }
                productCartListTemp.addAll(productCartList);
                editor.putStringSet("productCart", productCartListTemp);
                editor.commit();
                Intent intent = new Intent(view.getContext(), CartActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

    }
}
