package com.gisass.browser.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gisass.browser.R;
import com.gisass.browser.databinding.JobAdapterViewBinding;
import com.gisass.browser.models.JobModel;
import com.gisass.browser.viewModels.JobViewModel;

import rx.functions.Action1;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.GridViewHolder> {

    private JobViewModel JobViewModel;
    private JobAdapterViewBinding jobAdapterViewBinding;
    private Action1<String> url;

    public JobAdapter(JobViewModel JobViewModel) {
        this.JobViewModel = JobViewModel;
    }


    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.job_adapter_view, parent, false);
        jobAdapterViewBinding = DataBindingUtil.bind(view);

        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GridViewHolder holder, final int position) {
        final JobModel jobModel = JobViewModel.getJobModels().get(position);
        jobAdapterViewBinding.setViewModel(jobModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url.call(jobModel.getJobUrl());
                //Toast.makeText(holder.itemView.getContext(), "item clicked " + position, Toast.LENGTH_SHORT).show();
            }
        });
        jobAdapterViewBinding.executePendingBindings();
    }

    public void setUrl(Action1<String> url) {
        this.url = url;
    }

    @Override
    public int getItemCount() {
        return JobViewModel.getJobModels().size();
    }

    class GridViewHolder extends RecyclerView.ViewHolder {
        GridViewHolder(View rootView) {
            super(rootView);
        }
    }
}
