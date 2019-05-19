package com.gisass.browser.viewModels;

import android.app.Activity;
import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;

import com.gisass.browser.R;
import com.gisass.browser.activities.MainActivity;
import com.gisass.browser.adapters.ViewInTabAdapter;
import com.gisass.browser.customViews.CustomDialogs;
import com.gisass.browser.globalClass.MyApp;

import java.util.ArrayList;

import rx.functions.Action1;

public class ViewInTabViewModel extends AndroidViewModel {

    private ArrayList<Object> dataArray;
    private SocialViewModel staticIconViewModel;
    private EducationAndJobViewModel gridIconWithBackgroundViewModel;
    private AppSettingViewModel appSettingViewModel;
    private NewsViewModel newsViewModel;
    private ViewInTabAdapter viewInTabAdapter;
    private Action1<String> urlObserver;
    private String[] bottomSheetUrls;


    public ViewInTabViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        dataArray = new ArrayList<>();
        bottomSheetUrls = getApplication().getResources().getStringArray(R.array.bottom_sheet_items);
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
        staticIconViewModel = new SocialViewModel(getApplication());
        gridIconWithBackgroundViewModel = new EducationAndJobViewModel();
        appSettingViewModel = new AppSettingViewModel();
        newsViewModel = new NewsViewModel(getApplication());
        initializeAllModels();

    }

    private void initializeAllModels() {
        staticIconViewModel.init();
        gridIconWithBackgroundViewModel.init();
        appSettingViewModel.init();
        newsViewModel.init();
    }

    public void setActionUrlToAdapters(Action1 url) {
        this.urlObserver = url;
        staticIconViewModel.getAdapter().setUrl(url);
        gridIconWithBackgroundViewModel.getAdapter().setUrl(url);
    }


    private void getAllModels() {
        dataArray.add(staticIconViewModel.getStaticIconModels());
        dataArray.add(gridIconWithBackgroundViewModel.getStaticIconModels());
        dataArray.add(appSettingViewModel.getAppSettingModels());
        dataArray.add("News");
        dataArray.add(newsViewModel.getNewsModels());
        viewInTabAdapter.notifyDataSetChanged();
    }

    public void onBottomSheetClick(int index, View view) {
        switch (index) {
            case 0:
                onAdvertisementItemClick(view);
                break;
            case 1:
                urlObserver.call(bottomSheetUrls[1]);
                break;
            case 2:

                break;
            case 3:
                urlObserver.call(bottomSheetUrls[3]);
                break;
            case 4:
                urlObserver.call(bottomSheetUrls[4]);
                break;

        }
    }

    private void onAdvertisementItemClick(View view) {
        final Activity activity = ((MyApp) getApplication()).getCurrentActivity();
        final String[] urls = activity.getResources().getStringArray(R.array.advertisement_ads);

        CustomDialogs.getInstance().getAdvertisementTooltip(activity, view).observe((MainActivity) activity, new Observer<Integer>() {
            @Override
            public void onChanged(Integer s) {
                switch (s) {
                    case 1:
                        urlObserver.call(urls[0]);
                        break;
                    case 2:
                        urlObserver.call(urls[1]);
                        break;
                    case 3:
                        urlObserver.call(urls[2]);
                        break;
                }
            }
        });
    }

}
