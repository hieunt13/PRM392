package com.example.hotgearvn.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotgearvn.R;
import com.example.hotgearvn.activity.ProductDetailActivity;
import com.example.hotgearvn.entities.Product;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class ImageSliderProductAdapter  extends SliderViewAdapter<ImageSliderProductAdapter.SliderAdapterVH> {
    private Context context;
    private List<Product> mSliderProduct;

    public ImageSliderProductAdapter(Context context, List<Product> mSliderProduct) {
        this.context = context;
        this.mSliderProduct = mSliderProduct;
    }

    public void renewItems(List<Product> mSliderProduct) {
        this.mSliderProduct = mSliderProduct;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderProduct.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(Product product) {
        this.mSliderProduct.add(product);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_slider_product, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        Product product = mSliderProduct.get(position);
        viewHolder.imageViewBackground.setImageResource(product.getImage());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
                intent.putExtra("productId", mSliderProduct.get(position).getProductId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderProduct.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }
}
