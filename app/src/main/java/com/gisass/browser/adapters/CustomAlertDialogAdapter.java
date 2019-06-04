package com.gisass.browser.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.fenchtose.tooltip.Tooltip;
import com.gisass.browser.R;
import com.gisass.browser.databinding.AdapterCustomAlertBinding;

public class CustomAlertDialogAdapter extends RecyclerView.Adapter<CustomAlertDialogAdapter.GridViewHolder> {

    private String[] items;
    private AdapterCustomAlertBinding adapterCustomAlertBinding;


    public CustomAlertDialogAdapter(String[] items) {
        this.items = items;

    }


    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_custom_alert, parent, false);
        adapterCustomAlertBinding = DataBindingUtil.bind(view);

        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        adapterCustomAlertBinding.setLabel(items[position]);

        adapterCustomAlertBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return items.length;
    }


    class GridViewHolder extends RecyclerView.ViewHolder {
        GridViewHolder(View rootView) {
            super(rootView);
        }
    }
}
