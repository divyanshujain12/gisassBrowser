package com.gisass.browser.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gisass.browser.adapters.GridLayoutAdapter;
import com.gisass.browser.models.StaticIconModel;

import java.util.ArrayList;

public class StaticIconViewModel extends ViewModel {

    private MutableLiveData<ArrayList<StaticIconModel>> staticIconModels;
    private GridLayoutAdapter gridLayoutAdapter;

    public StaticIconViewModel() {

    }

    public void init() {
        gridLayoutAdapter = new GridLayoutAdapter(this);
    }

    public MutableLiveData<ArrayList<StaticIconModel>> getStaticIconModels() {
        return staticIconModels;
    }

    public void setStaticIconModels(MutableLiveData<ArrayList<StaticIconModel>> staticIconModels) {
        this.staticIconModels = staticIconModels;
        gridLayoutAdapter.notifyDataSetChanged();
    }

    public StaticIconModel getStaticIconModel(int position) {
        if (this.staticIconModels != null && this.staticIconModels.getValue().size() > 0)
            return this.staticIconModels.getValue().get(position);
        else
            return null;
    }

    public GridLayoutAdapter getAdapter() {
        return gridLayoutAdapter;
    }
}

