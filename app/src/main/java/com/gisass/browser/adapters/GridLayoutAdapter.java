package com.gisass.browser.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gisass.browser.R;
import com.gisass.browser.viewModels.StaticIconViewModel;
import com.gisass.browser.databinding.GridAdapterLayoutBinding;

public class GridLayoutAdapter extends RecyclerView.Adapter<GridLayoutAdapter.GridViewHolder> {

    private StaticIconViewModel staticIconViewModel;
    private GridAdapterLayoutBinding gridAdapterLayoutBinding;

    public GridLayoutAdapter(StaticIconViewModel staticIconViewModel) {
        this.staticIconViewModel = staticIconViewModel;
    }


    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.grid_adapter_layout, parent);
        gridAdapterLayoutBinding = DataBindingUtil.bind(view);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {

        gridAdapterLayoutBinding.setViewModel(staticIconViewModel.getStaticIconModel(position));
        gridAdapterLayoutBinding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return staticIconViewModel.getStaticIconModels().getValue().size();
    }


    class GridViewHolder extends RecyclerView.ViewHolder {
        GridViewHolder(View rootView) {
            super(rootView);
        }
    }
}
