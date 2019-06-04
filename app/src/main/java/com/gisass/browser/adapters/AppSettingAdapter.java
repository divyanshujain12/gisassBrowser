package com.gisass.browser.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.gisass.browser.R;
import com.gisass.browser.customViews.CustomDialogs;
import com.gisass.browser.databinding.AdapterAppSettingBinding;
import com.gisass.browser.viewModels.AppSettingViewModel;

public class AppSettingAdapter extends RecyclerView.Adapter<AppSettingAdapter.GridViewHolder> {

    private AppSettingViewModel appSettingViewModel;
    private AdapterAppSettingBinding adapterAppSettingBinding;



    private MutableLiveData<String> url;

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
    public void onBindViewHolder(@NonNull GridViewHolder holder, final int position) {

        adapterAppSettingBinding.setViewModel(appSettingViewModel.getAppSettingModel(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        shareLinkToSocialMedia(v.getContext(),"http://gisass.com/");
                        break;
                    case 1:

                        break;
                    case 2:
                        CustomDialogs.getInstance().showCustomPopup(v.getContext(), v.getContext().getResources().getStringArray(R.array.user_options_array), v);
                        break;
                    case 3:
                        CustomDialogs.getInstance().showCustomPopup(v.getContext(), v.getContext().getResources().getStringArray(R.array.spoints_array), v);
                        break;
                }
            }
        });
        adapterAppSettingBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return appSettingViewModel.getAppSettingModels().size();
    }

    public void setUrl(MutableLiveData<String> url) {
        this.url = url;
    }
    class GridViewHolder extends RecyclerView.ViewHolder {
        GridViewHolder(View rootView) {
            super(rootView);
        }
    }

    private void shareLinkToSocialMedia(Context context, String link) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + link;
            //shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            context.startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }
}
