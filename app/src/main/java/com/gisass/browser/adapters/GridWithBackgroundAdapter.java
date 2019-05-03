package com.gisass.browser.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gisass.browser.R;
import com.gisass.browser.databinding.GridWithBackgroundAdapterLayoutBinding;
import com.gisass.browser.viewModels.EducationAndJobViewModel;

public class GridWithBackgroundAdapter extends RecyclerView.Adapter<GridWithBackgroundAdapter.GridViewHolder> {

    private EducationAndJobViewModel staticIconViewModel;
    private GridWithBackgroundAdapterLayoutBinding gridWithBackgroundAdapterLayoutBinding;

    public GridWithBackgroundAdapter(EducationAndJobViewModel staticIconViewModel) {
        this.staticIconViewModel = staticIconViewModel;
    }


    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.grid_with_background_adapter_layout, parent, false);
        gridWithBackgroundAdapterLayoutBinding = DataBindingUtil.bind(view);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {

        gridWithBackgroundAdapterLayoutBinding.setViewModel(staticIconViewModel.getStaticIconModel(position));
        gridWithBackgroundAdapterLayoutBinding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return staticIconViewModel.getStaticIconModels().size();
    }


    class GridViewHolder extends RecyclerView.ViewHolder {
        GridViewHolder(View rootView) {
            super(rootView);
        }
    }
}
