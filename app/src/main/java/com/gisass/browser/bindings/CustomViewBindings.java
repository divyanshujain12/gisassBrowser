package com.gisass.browser.bindings;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gisass.browser.R;
import com.squareup.picasso.Picasso;

public class CustomViewBindings {
    private static String IMAGE_BASE_URL = "http://peeran.com/gisass/uploads/";

    @BindingAdapter("setGridAdapter")
    public static void bindGridRecyclerViewAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 4));
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("setLinearAdapter")
    public static void bindLinearRecyclerViewAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, int resource) {
        Picasso.with(imageView.getContext()).load(resource).error(R.drawable.logo).into(imageView);
        //imageView.setImageResource(resource);
    }

    @BindingAdapter({"android:src"})
    public static void setImageViewResource(ImageView imageView, String resource) {
        Picasso.with(imageView.getContext()).load(IMAGE_BASE_URL + resource).placeholder(R.drawable.logo).error(R.drawable.logo).into(imageView);
        //imageView.setImageResource(resource);
    }
}
