package com.gisass.browser.viewModels;

import androidx.lifecycle.ViewModel;

import com.gisass.browser.adapters.GridLayoutAdapter;
import com.gisass.browser.models.StaticIconModel;

import java.util.ArrayList;

public class StaticIconViewModel extends ViewModel {

    private ArrayList<StaticIconModel> staticIconModels;
    private GridLayoutAdapter gridLayoutAdapter;

    public StaticIconViewModel() {
        gridLayoutAdapter = new GridLayoutAdapter(this);
    }

    public ArrayList<StaticIconModel> getStaticIconModels() {
        return staticIconModels;
    }

    public void setStaticIconModels(ArrayList<StaticIconModel> staticIconModels) {
        this.staticIconModels = staticIconModels;
        gridLayoutAdapter.notifyDataSetChanged();
    }

    public StaticIconModel getStaticIconModel(int position) {
        if (this.staticIconModels != null && this.staticIconModels.size() > 0)
            return this.staticIconModels.get(position);
        else
            return null;
    }

    public GridLayoutAdapter getAdapter() {
        return gridLayoutAdapter;
    }
}

