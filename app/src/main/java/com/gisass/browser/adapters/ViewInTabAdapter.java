package com.gisass.browser.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gisass.browser.R;
import com.gisass.browser.databinding.GridAdapterLayoutBinding;
import com.gisass.browser.databinding.GridWithBackgroundAdapterLayoutBinding;
import com.gisass.browser.models.StaticIconModel;
import com.gisass.browser.models.StaticIconWithBackgroundModel;
import com.gisass.browser.viewModels.ViewInTabViewModel;

public class ViewInTabAdapter extends RecyclerView.Adapter<ViewInTabAdapter.ViewInTabHolder> {

    private ViewInTabViewModel viewInTabViewModel;
    private GridAdapterLayoutBinding gridAdapterLayoutBinding;
    private GridWithBackgroundAdapterLayoutBinding gridWithBackgroundAdapterLayoutBinding;

    public ViewInTabAdapter(ViewInTabViewModel staticIconViewModel) {
        this.viewInTabViewModel = staticIconViewModel;
    }


    @NonNull
    @Override
    public ViewInTabHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = null;
        switch (viewType) {
            case 1:
                view = layoutInflater.inflate(R.layout.grid_adapter_layout, parent, false);
                gridAdapterLayoutBinding = DataBindingUtil.bind(view);
                break;
            case 2:
                view = layoutInflater.inflate(R.layout.grid_with_background_adapter_layout, parent, false);
                gridWithBackgroundAdapterLayoutBinding = DataBindingUtil.bind(view);
                break;
        }


        return new ViewInTabHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewInTabHolder holder, int position) {

        switch (getItemViewType(position)) {
            case 1:
                gridAdapterLayoutBinding.setViewModel((StaticIconModel) viewInTabViewModel.getItem(position));
                gridAdapterLayoutBinding.executePendingBindings();
                break;
            case 2:
                gridWithBackgroundAdapterLayoutBinding.setViewModel((StaticIconWithBackgroundModel) viewInTabViewModel.getItem(position));
                gridWithBackgroundAdapterLayoutBinding.executePendingBindings();
                break;
        }
    }

    @Override
    public int getItemCount() {
        return viewInTabViewModel.getDataArrayModels().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (viewInTabViewModel.getItem(position) instanceof StaticIconModel)
            return 1;
        else if (viewInTabViewModel.getItem(position) instanceof StaticIconWithBackgroundModel)
            return 2;
        else
            return 3;
    }

    class ViewInTabHolder extends RecyclerView.ViewHolder {
        ViewInTabHolder(View rootView) {
            super(rootView);
        }
    }
}
