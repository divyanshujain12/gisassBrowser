package com.gisass.browser.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gisass.browser.R;
import com.gisass.browser.databinding.AdapterAppSettingBinding;
import com.gisass.browser.viewModels.AppSettingViewModel;

public class AppSettingAdapter extends RecyclerView.Adapter<AppSettingAdapter.GridViewHolder> {

    private AppSettingViewModel appSettingViewModel;

    private AdapterAppSettingBinding adapterAppSettingBinding;

    public AppSettingAdapter(AppSettingViewModel appSettingViewModel) {
        this.appSettingViewModel = appSettingViewModel;
    }


    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_app_setting, parent, false);
        adapterAppSettingBinding = DataBindingUtil.bind(view);

        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {

        adapterAppSettingBinding.setViewModel(appSettingViewModel.getAppSettingModel(position));
        adapterAppSettingBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return appSettingViewModel.getAppSettingModels().size();
    }


    class GridViewHolder extends RecyclerView.ViewHolder {
        GridViewHolder(View rootView) {
            super(rootView);
        }
    }
}
