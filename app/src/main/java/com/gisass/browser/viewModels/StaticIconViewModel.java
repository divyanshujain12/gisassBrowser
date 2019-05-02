package com.gisass.browser.viewModels;

import androidx.lifecycle.ViewModel;

import com.gisass.browser.R;
import com.gisass.browser.adapters.GridLayoutAdapter;
import com.gisass.browser.models.StaticIconModel;

import java.util.ArrayList;

public class StaticIconViewModel extends ViewModel {

    private String[] appNames = {"Google", "Facebook", "Instagram", "Youtube", "Amazon", "Flipkart", "Hotstar", "Bookmyshow", "HotVideo", "PlayStore", "Travel", "Cricket"};
    private int[] appIcons = {R.drawable.google, R.drawable.facebook, R.drawable.instagram, R.drawable.youtube, R.drawable.amazon, R.drawable.flipkart, R.drawable.hotstar, R.drawable.bookmyshow, R.drawable.hotvideos, R.drawable.playstore, R.drawable.booking, R.drawable.cricket};
    private ArrayList<StaticIconModel> staticIconModels;
    private GridLayoutAdapter gridLayoutAdapter;

    public StaticIconViewModel() {

    }

    public void init() {
        gridLayoutAdapter = new GridLayoutAdapter(this);
        staticIconModels = new ArrayList<>();
        for (int i = 0; i < appIcons.length; i++) {
            StaticIconModel staticIconModel = new StaticIconModel();
            staticIconModel.setIconName(appNames[i]);
            staticIconModel.setIconResourceId(appIcons[i]);
            staticIconModels.add(staticIconModel);

        }
        setStaticIconModels();
    }

    public ArrayList<StaticIconModel> getStaticIconModels() {
        return staticIconModels;
    }

    private void setStaticIconModels() {
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

