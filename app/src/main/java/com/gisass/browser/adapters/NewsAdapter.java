package com.gisass.browser.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gisass.browser.R;
import com.gisass.browser.databinding.AdapterNewsBinding;
import com.gisass.browser.viewModels.NewsViewModel;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.GridViewHolder> {

    private NewsViewModel newsViewModel;
    private AdapterNewsBinding adapterNewsBinding;

    public NewsAdapter(NewsViewModel newsViewModel) {
        this.newsViewModel = newsViewModel;
    }


    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_news, parent, false);
        adapterNewsBinding = DataBindingUtil.bind(view);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        adapterNewsBinding.setViewModel(newsViewModel.getNewsModel(position));
        adapterNewsBinding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return newsViewModel.getNewsModels().size();
    }


    class GridViewHolder extends RecyclerView.ViewHolder {
        GridViewHolder(View rootView) {
            super(rootView);
        }
    }
}
