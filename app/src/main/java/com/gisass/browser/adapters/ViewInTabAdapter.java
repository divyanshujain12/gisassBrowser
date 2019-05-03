package com.gisass.browser.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gisass.browser.R;
import com.gisass.browser.databinding.ActivityGridRecyclerViewBinding;
import com.gisass.browser.databinding.ActivityGridWithBackgroundBinding;
import com.gisass.browser.databinding.HeadingLayoutBinding;
import com.gisass.browser.models.StaticIconModel;
import com.gisass.browser.models.StaticIconWithBackgroundModel;
import com.gisass.browser.viewModels.ViewInTabViewModel;

import java.util.ArrayList;

public class ViewInTabAdapter extends RecyclerView.Adapter<ViewInTabAdapter.ViewInTabHolder> {

    private ViewInTabViewModel viewInTabViewModel;
    private ActivityGridRecyclerViewBinding activityGridRecyclerViewBinding;
    private ActivityGridWithBackgroundBinding activityGridWithBackgroundBinding;
    private HeadingLayoutBinding headingLayoutBinding;
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
                view = layoutInflater.inflate(R.layout.activity_grid_recycler_view, parent, false);
                activityGridRecyclerViewBinding = DataBindingUtil.bind(view);
                break;
            case 2:
                view = layoutInflater.inflate(R.layout.activity_grid_with_background, parent, false);
                activityGridWithBackgroundBinding = DataBindingUtil.bind(view);
                break;

            case 3:
                view = layoutInflater.inflate(R.layout.heading_layout, parent, false);
                headingLayoutBinding = DataBindingUtil.bind(view);
                break;
        }


        return new ViewInTabHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewInTabHolder holder, int position) {

        switch (getItemViewType(position)) {
            case 1:
                activityGridRecyclerViewBinding.setViewModel(viewInTabViewModel.getStaticIconViewModel());
                activityGridRecyclerViewBinding.executePendingBindings();
                break;
            case 2:
                activityGridWithBackgroundBinding.setViewModel(viewInTabViewModel.getGridIconWithBackgroundViewModel());
                activityGridWithBackgroundBinding.executePendingBindings();
                break;
            case 3:
                headingLayoutBinding.setTitle((String) viewInTabViewModel.getItem(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return viewInTabViewModel.getDataArrayModels().size();
    }

    @Override
    public int getItemViewType(int position) {

        Object object = viewInTabViewModel.getDataArrayModels().get(position);

        if (object instanceof ArrayList) {
            Object innerObj = ((ArrayList) object).get(0);
            if (innerObj instanceof StaticIconModel)
                return 1;
            else if (innerObj instanceof StaticIconWithBackgroundModel)
                return 2;


        } else {
            return 3;
        }
        return 3;
//        if (viewInTabViewModel.getItem(position) instanceof StaticIconModel)
//            return 1;
//        else if (viewInTabViewModel.getItem(position) instanceof StaticIconWithBackgroundModel)
//            return 2;
//        else
//            return 3;
    }

    class ViewInTabHolder extends RecyclerView.ViewHolder {
        ViewInTabHolder(View rootView) {
            super(rootView);
        }
    }
}
