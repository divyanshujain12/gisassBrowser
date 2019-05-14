package com.gisass.browser.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.gisass.browser.R;
import com.gisass.browser.adapters.ViewInTabAdapter;
import com.gisass.browser.customViews.CustomDialogs;
import com.gisass.browser.globalClass.MyApp;

import java.util.ArrayList;

public class ViewInTabViewModel extends AndroidViewModel {

    private ArrayList<Object> dataArray;
    private SocialViewModel staticIconViewModel;
    private EducationAndJobViewModel gridIconWithBackgroundViewModel;
    private AppSettingViewModel appSettingViewModel;
    private NewsViewModel newsViewModel;
    private ViewInTabAdapter viewInTabAdapter;
    private MutableLiveData<String> url;
    private String[] bottomSheetUrls;


    public MutableLiveData<String> getBottomSheetItemClick() {
        return bottomSheetItemClick;
    }

    private MutableLiveData<String> bottomSheetItemClick;

    public ViewInTabViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        dataArray = new ArrayList<>();
        url = new MutableLiveData<>();
        bottomSheetItemClick = new MutableLiveData<>();
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
        setMutableUrlToAdapters();
    }

    private void initializeAllModels() {
        staticIconViewModel.init();
        gridIconWithBackgroundViewModel.init();
        appSettingViewModel.init();
        newsViewModel.init();
    }

    private void setMutableUrlToAdapters() {
        staticIconViewModel.getAdapter().setUrl(url);
    }

    public MutableLiveData<String> getUrl() {
        return url;
    }

    private void getAllModels() {
        dataArray.add(staticIconViewModel.getStaticIconModels());
        dataArray.add(gridIconWithBackgroundViewModel.getStaticIconModels());
        dataArray.add(appSettingViewModel.getAppSettingModels());
        dataArray.add("News");
        dataArray.add(newsViewModel.getNewsModels());
        viewInTabAdapter.notifyDataSetChanged();
    }

    public void onBottomSheetClick(int index) {
        switch (index) {
            case 0:
                CustomDialogs.getInstance().getAdvertisementPopup(((MyApp) getApplication()).getCurrentActivity(), bottomSheetItemClick);
                break;
            case 1:
                bottomSheetItemClick.postValue(bottomSheetUrls[1]);
                break;
            case 2:

                break;
            case 3:
                bottomSheetItemClick.postValue(bottomSheetUrls[3]);
                break;
            case 4:
                bottomSheetItemClick.postValue(bottomSheetUrls[4]);
                break;

        }
    }

}
