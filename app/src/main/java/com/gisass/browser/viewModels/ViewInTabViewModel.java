package com.gisass.browser.viewModels;

import androidx.lifecycle.ViewModel;

import com.gisass.browser.adapters.ViewInTabAdapter;

import java.util.ArrayList;

public class ViewInTabViewModel extends ViewModel {

    private ArrayList<Object> dataArray;
    private StaticIconViewModel staticIconViewModel;
    private GridIconWithBackgroundViewModel gridIconWithBackgroundViewModel;
    private ViewInTabAdapter viewInTabAdapter;

    public void init() {
        dataArray = new ArrayList<>();
        staticIconViewModel = new StaticIconViewModel();
        gridIconWithBackgroundViewModel = new GridIconWithBackgroundViewModel();
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

    public StaticIconViewModel getStaticIconViewModel() {
        return staticIconViewModel;
    }

    public GridIconWithBackgroundViewModel getGridIconWithBackgroundViewModel() {
        return gridIconWithBackgroundViewModel;
    }

    private void initAllViewModel() {
        staticIconViewModel.init();
        gridIconWithBackgroundViewModel.init();
    }

    private void getAllModels() {
        dataArray.addAll(staticIconViewModel.getStaticIconModels());
        dataArray.addAll(gridIconWithBackgroundViewModel.getStaticIconModels());
        viewInTabAdapter.notifyDataSetChanged();
    }

}
