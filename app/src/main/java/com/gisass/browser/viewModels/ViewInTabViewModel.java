package com.gisass.browser.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.gisass.browser.adapters.ViewInTabAdapter;

import java.util.ArrayList;

public class ViewInTabViewModel extends AndroidViewModel {

    private ArrayList<Object> dataArray;
    private SocialViewModel staticIconViewModel;
    private EducationAndJobViewModel gridIconWithBackgroundViewModel;
    private AppSettingViewModel appSettingViewModel;
    private NewsViewModel newsViewModel;
    private ViewInTabAdapter viewInTabAdapter;

    public ViewInTabViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        dataArray = new ArrayList<>();

        viewInTabAdapter = new ViewInTabAdapter(this);
        initAllViewModel();
        getAllModels();
    }

    public ArrayList<Object> getDataArrayModels() {
        return dataArray;
    }

    public ViewInTabAdapter getAdapter() {
        return viewInTabAdapter;
    }

    public Object getItem(int position) {
        return dataArray.get(position);
    }

    public SocialViewModel getStaticIconViewModel() {
        return staticIconViewModel;
    }

    public EducationAndJobViewModel getGridIconWithBackgroundViewModel() {
        return gridIconWithBackgroundViewModel;
    }

    public AppSettingViewModel getAppSettingViewModel() {
        return appSettingViewModel;
    }

    public NewsViewModel getNewsViewModel() {
        return newsViewModel;
    }

    private void initAllViewModel() {
        staticIconViewModel = new SocialViewModel();
        gridIconWithBackgroundViewModel = new EducationAndJobViewModel();
        appSettingViewModel = new AppSettingViewModel();
        newsViewModel = new NewsViewModel(getApplication());
        staticIconViewModel.init();
        gridIconWithBackgroundViewModel.init();
        appSettingViewModel.init();
        newsViewModel.init();
    }

    private void getAllModels() {
        dataArray.add("Social");
        dataArray.add(staticIconViewModel.getStaticIconModels());
        dataArray.add("Education And Jobs");
        dataArray.add(gridIconWithBackgroundViewModel.getStaticIconModels());
        dataArray.add("App Setting");
        dataArray.add(appSettingViewModel.getAppSettingModels());
        dataArray.add("News");
        dataArray.add(newsViewModel.getNewsModels());
        viewInTabAdapter.notifyDataSetChanged();
    }

}
