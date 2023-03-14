package com.example.hotgearvn.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotgearvn.R;
import com.example.hotgearvn.entities.Product;
import com.example.hotgearvn.item.ItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class RecyclerViewCartAdapter extends RecyclerView.Adapter<RecyclerViewCartAdapter.ViewHolder> {
    private ArrayList<Product> cartProductList;
    HashMap<Long, Integer> cartList = new HashMap<>();
    private Context context;

    public RecyclerViewCartAdapter(ArrayList<Product> cartProductList,Context context) {
        this.cartProductList = cartProductList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cart_row_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewCartAdapter.ViewHolder holder, int position) {
        Product productInCart = cartProductList.get(position);
        int quantity = 0;
        SharedPreferences sharedpreferences = context.getSharedPreferences("ProductInCart", context.MODE_PRIVATE);
        Set<String> productCartList = sharedpreferences.getStringSet("productCart", new HashSet<String>());
        for (String cartProduct : productCartList) {
            String[] productWithQuantity = cartProduct.split(",");
            if(productInCart.getProductId().toString().equals(productWithQuantity[0])){
                quantity = Integer.parseInt(productWithQuantity[1]);
            }
        }
        holder.productImg.setImageResource(productInCart.getImage());
        holder.productName.setText(productInCart.getName());
        holder.productPrice.setText(productInCart.getPrice().intValue() + " đ");
        holder.productQuantity.setText(quantity+"");

        holder.btnPlus.setOnClickListener(view -> {
            boolean added = false;
            int quantity1 = Integer.valueOf(holder.productQuantity.getText().toString());
            holder.productQuantity.setText(String.valueOf(quantity1+1));
            SharedPreferences sharedpreferencesP = view.getContext().getSharedPreferences("ProductInCart", view.getContext().MODE_PRIVATE);
            Set<String> productCartListP = sharedpreferencesP.getStringSet("productCart", new HashSet<String>());
            Set<String> productCartListTemp = new HashSet<String>();
            for (String cartProduct : productCartListP) {
                String[] productWithQuantity = cartProduct.split(",");
                if (productWithQuantity[0].equalsIgnoreCase(productInCart.getProductId().toString())) {
                    productCartListTemp.add(productInCart.getProductId() + "," + (Integer.valueOf(productWithQuantity[1]) + 1));
                    added = true;
                    productCartList.remove(cartProduct);
                    break;
                }
            }
            if (!added) {
                productCartListTemp.add(productInCart.getProductId() + "," + 1);
            }
            productCartListTemp.addAll(productCartList);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putStringSet("productCart", productCartListTemp);
            editor.commit();
        });

        holder.btnMinus.setOnClickListener(view -> {
            int quantity2 = Integer.valueOf(holder.productQuantity.getText().toString());
            if (quantity2 <= 1) {
                holder.productQuantity.setText("1");
            } else {
                boolean added = false;
                holder.productQuantity.setText(String.valueOf(quantity2-1));
                SharedPreferences sharedpreferencesP = view.getContext().getSharedPreferences("ProductInCart", view.getContext().MODE_PRIVATE);
                Set<String> productCartListP = sharedpreferencesP.getStringSet("productCart", new HashSet<String>());
                Set<String> productCartListTemp = new HashSet<String>();
                for (String cartProduct : productCartListP) {
                    String[] productWithQuantity = cartProduct.split(",");
                    if (productWithQuantity[0].equalsIgnoreCase(productInCart.getProductId().toString())) {
                        productCartListTemp.add(productInCart.getProductId() + "," + (Integer.valueOf(productWithQuantity[1]) - 1));
                        added = true;
                        productCartList.remove(cartProduct);
                        break;
                    }
                }
                if (!added) {
                    productCartListTemp.add(productInCart.getProductId() + "," + 1);
                }
                productCartListTemp.addAll(productCartList);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putStringSet("productCart", productCartListTemp);
                editor.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartProductList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView productImg;
        TextView productName, productPrice, productQuantity, btnPlus, btnMinus;

        private ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            productImg = itemView.findViewById(R.id.ivProductImage);
            productName = itemView.findViewById(R.id.tvProductName);
            productPrice = itemView.findViewById(R.id.tvProductPrice);
            productQuantity = itemView.findViewById(R.id.tvProductQuantity);
            btnPlus = itemView.findViewById(R.id.productQuantityPlus);
            btnMinus = itemView.findViewById(R.id.productQuantityMinus);

        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

    }

}
