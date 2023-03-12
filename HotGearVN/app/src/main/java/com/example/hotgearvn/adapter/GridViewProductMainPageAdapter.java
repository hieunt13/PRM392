package com.example.hotgearvn.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotgearvn.R;
import com.example.hotgearvn.activity.ProductDetailActivity;
import com.example.hotgearvn.entities.Product;

import java.util.List;

public class GridViewProductMainPageAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Product> productList;

    public GridViewProductMainPageAdapter(Context context, int layout, List<Product> productList) {
        this.context = context;
        this.layout = layout;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        TextView tvName = (TextView)  view.findViewById(R.id.textViewNameMain);
        TextView tvPrice = (TextView)  view.findViewById(R.id.textViewPriceMain);
        ImageView ivImage = (ImageView) view.findViewById(R.id.imageViewProductMain);
        Button btnAddCart = view.findViewById(R.id.buttonAddToCartMain);
        Button btnBuy = view.findViewById(R.id.buttonBuyMain);
        Product product = productList.get(i);
        tvName.setText(product.getName());
        tvPrice.setText(product.getPrice().intValue()+" đ");
        ivImage.setImageResource(product.getImage());
        return view;
    }


}