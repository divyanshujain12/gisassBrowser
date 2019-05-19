package com.gisass.browser.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gisass.browser.R;
import com.gisass.browser.databinding.AdapterEducationViewBinding;
import com.gisass.browser.models.EducationModel;
import com.gisass.browser.viewModels.EducationViewModel;

import rx.functions.Action1;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.GridViewHolder> {

    private EducationViewModel educationViewModel;
    private AdapterEducationViewBinding adapterEducationViewBinding;
    private Action1<String> url;

    public EducationAdapter(EducationViewModel educationViewModel) {
        this.educationViewModel = educationViewModel;
    }


    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_education_view, parent, false);
        adapterEducationViewBinding = DataBindingUtil.bind(view);

        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GridViewHolder holder, final int position) {
        final EducationModel educationModel = educationViewModel.getEducationModels().get(position);
        adapterEducationViewBinding.setViewModel(educationModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url.call(educationModel.getUrl());
                //Toast.makeText(holder.itemView.getContext(), "item clicked " + position, Toast.LENGTH_SHORT).show();
            }
        });
        adapterEducationViewBinding.executePendingBindings();
    }

    public void setUrl(Action1<String> url) {
        this.url = url;
    }

    @Override
    public int getItemCount() {
        return educationViewModel.getEducationModels().size();
    }

    class GridViewHolder extends RecyclerView.ViewHolder {
        GridViewHolder(View rootView) {
            super(rootView);
        }
    }
}
