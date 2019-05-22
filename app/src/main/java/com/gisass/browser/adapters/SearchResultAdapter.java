package com.gisass.browser.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gisass.browser.R;
import com.gisass.browser.databinding.AdapterSearchResultBinding;
import com.gisass.browser.models.Item;
import com.gisass.browser.viewModels.SearchResultViewModel;

import rx.functions.Action1;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {

    private SearchResultViewModel searchResultViewModel;
    private AdapterSearchResultBinding adapterSearchResultBinding;


    private Action1<String> url;

    public SearchResultAdapter(SearchResultViewModel searchResultViewModel) {
        this.searchResultViewModel = searchResultViewModel;

    }


    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_search_result, parent, false);
        adapterSearchResultBinding = DataBindingUtil.bind(view);
        SearchResultViewHolder holder = new SearchResultViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        final Item item = searchResultViewModel.getGoogleSearchModel().get(position);
        adapterSearchResultBinding.setModel(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url.call(item.getFormattedUrl());
            }
        });
        adapterSearchResultBinding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        if (searchResultViewModel.getGoogleSearchModel() != null)
            return searchResultViewModel.getGoogleSearchModel().size();
        return 0;
    }


    public AdapterSearchResultBinding getAdapterSearchResultBinding() {
        return adapterSearchResultBinding;
    }

    public void setUrl(Action1<String> url) {
        this.url = url;
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder {
        SearchResultViewHolder(View rootView) {
            super(rootView);
        }
    }
}

