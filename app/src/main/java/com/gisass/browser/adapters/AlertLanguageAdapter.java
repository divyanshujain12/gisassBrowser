package com.gisass.browser.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gisass.browser.R;
import com.gisass.browser.databinding.AdapterAlertLanguageLayoutBinding;

public class AlertLanguageAdapter extends RecyclerView.Adapter<AlertLanguageAdapter.GridViewHolder> {

    private String[] languages;
    private AdapterAlertLanguageLayoutBinding adapterAlertLanguageLayoutBinding;
private AlertDialog alertDialog;
    public AlertLanguageAdapter(Context context, AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
        languages = context.getResources().getStringArray(R.array.languagesArray);
    }


    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_alert_language_layout, parent, false);
        adapterAlertLanguageLayoutBinding = DataBindingUtil.bind(view);

        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        String language = languages[position];
        adapterAlertLanguageLayoutBinding.setLanguage(language);
        adapterAlertLanguageLayoutBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return languages.length;
    }


    class GridViewHolder extends RecyclerView.ViewHolder {
        GridViewHolder(View rootView) {
            super(rootView);
        }
    }
}
