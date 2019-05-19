package com.gisass.browser.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.gisass.browser.R;
import com.gisass.browser.databinding.GridWithBackgroundAdapterLayoutBinding;
import com.gisass.browser.models.StaticIconWithBackgroundModel;
import com.gisass.browser.viewModels.EducationAndJobViewModel;

import rx.functions.Action1;

public class GridWithBackgroundAdapter extends RecyclerView.Adapter<GridWithBackgroundAdapter.GridViewHolder> {

    private EducationAndJobViewModel staticIconViewModel;
    private GridWithBackgroundAdapterLayoutBinding gridWithBackgroundAdapterLayoutBinding;
    private Action1<String> url;

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
    public void onBindViewHolder(@NonNull final GridViewHolder holder, int position) {
        final StaticIconWithBackgroundModel staticIconWithBackgroundModel = staticIconViewModel.getStaticIconModel(position);
        gridWithBackgroundAdapterLayoutBinding.setViewModel(staticIconWithBackgroundModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url.call(staticIconWithBackgroundModel.getIconName());
            }
        });
        gridWithBackgroundAdapterLayoutBinding.executePendingBindings();

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
