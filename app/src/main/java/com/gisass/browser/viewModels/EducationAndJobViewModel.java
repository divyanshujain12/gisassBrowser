package com.gisass.browser.viewModels;

import androidx.lifecycle.ViewModel;

import com.gisass.browser.R;
import com.gisass.browser.adapters.GridWithBackgroundAdapter;
import com.gisass.browser.models.StaticIconWithBackgroundModel;

import java.util.ArrayList;

public class EducationAndJobViewModel extends ViewModel {

    private String[] appNames = {"Education", "Jobs", "Games", "View All"};
    private int[] appIcons = {R.drawable.education, R.drawable.jobs, R.drawable.gamew, R.drawable.viewall};
    private ArrayList<StaticIconWithBackgroundModel> staticIconModels;

    private GridWithBackgroundAdapter gridWithBackgroundAdapter;

    public void init() {
        staticIconModels = new ArrayList<>();
        gridWithBackgroundAdapter = new GridWithBackgroundAdapter(this);
        for (int i = 0; i < appIcons.length; i++) {
            StaticIconWithBackgroundModel staticIconModel = new StaticIconWithBackgroundModel();
            staticIconModel.setIconName(appNames[i]);
            staticIconModel.setIconResourceId(appIcons[i]);
            staticIconModels.add(staticIconModel);

        }
        setStaticIconModels();
    }

    public StaticIconWithBackgroundModel getStaticIconModel(int position) {
        return staticIconModels.get(position);
    }

    public ArrayList<StaticIconWithBackgroundModel> getStaticIconModels() {
        return staticIconModels;
    }

    private void setStaticIconModels() {
        gridWithBackgroundAdapter.notifyDataSetChanged();
    }

    public GridWithBackgroundAdapter getAdapter() {
        return gridWithBackgroundAdapter;
    }
}
