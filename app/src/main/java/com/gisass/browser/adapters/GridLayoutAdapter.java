package com.gisass.browser.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.gisass.browser.R;
import com.gisass.browser.databinding.GridAdapterLayoutBinding;
import com.gisass.browser.viewModels.SocialViewModel;

import rx.functions.Action1;

public class GridLayoutAdapter extends RecyclerView.Adapter<GridLayoutAdapter.GridViewHolder> {

    private SocialViewModel staticIconViewModel;
    private GridAdapterLayoutBinding gridAdapterLayoutBinding;
    private Action1<String> url;
    private String[] allUrls;

    public GridLayoutAdapter(SocialViewModel staticIconViewModel) {
        this.staticIconViewModel = staticIconViewModel;
        allUrls = staticIconViewModel.getApplication().getResources().getStringArray(R.array.social_sites);

    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.grid_adapter_layout, parent, false);
        gridAdapterLayoutBinding = DataBindingUtil.bind(view);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, final int position) {

        gridAdapterLayoutBinding.setViewModel(staticIconViewModel.getStaticIconModel(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url.call(allUrls[position]);
            }
        });
        gridAdapterLayoutBinding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return staticIconViewModel.getStaticIconModels().size();
    }

    public void setUrl(Action1<String> url) {
        this.url = url;
    }

    class GridViewHolder extends RecyclerView.ViewHolder {
        GridViewHolder(View rootView) {
            super(rootView);
        }
    }
}
