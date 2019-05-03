package com.gisass.browser.viewModels;

import androidx.lifecycle.ViewModel;

import com.gisass.browser.R;
import com.gisass.browser.adapters.AppSettingAdapter;
import com.gisass.browser.models.AppSettingModel;

import java.util.ArrayList;

public class AppSettingViewModel extends ViewModel {

    private String[] appNames = {"Share", "Rating", "Users", "SPoints"};
    private int[] appIcons = {R.drawable.share, R.drawable.rate, R.drawable.user, R.drawable.starsport};
    private ArrayList<AppSettingModel> appSettingModels;
    private AppSettingAdapter appSettingAdapter;

    public void init() {
        appSettingModels = new ArrayList<>();
        appSettingAdapter = new AppSettingAdapter(this);
        for (int i = 0; i < appIcons.length; i++) {
            AppSettingModel staticIconModel = new AppSettingModel();
            staticIconModel.setIconName(appNames[i]);
            staticIconModel.setIconResourceId(appIcons[i]);
            appSettingModels.add(staticIconModel);

        }
        setStaticIconModels();
    }

    private void setStaticIconModels() {

    }

    public ArrayList<AppSettingModel> getAppSettingModels() {
        return appSettingModels;
    }

    public AppSettingModel getAppSettingModel(int pos) {
        return appSettingModels.get(pos);
    }

    public AppSettingAdapter getAppSettingAdapter() {
        return appSettingAdapter;
    }
}
