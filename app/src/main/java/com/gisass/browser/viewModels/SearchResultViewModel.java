package com.gisass.browser.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.gisass.browser.models.GoogleSearchModel;

import java.util.ArrayList;

public class SearchResultViewModel extends AndroidViewModel {

    public ArrayList<GoogleSearchModel> getGoogleSearchModels() {
        return googleSearchModels;
    }

    public void setGoogleSearchModels(ArrayList<GoogleSearchModel> googleSearchModels) {
        this.googleSearchModels = googleSearchModels;
    }

    ArrayList<GoogleSearchModel> googleSearchModels = new ArrayList<>();

    public SearchResultViewModel(@NonNull Application application) {
        super(application);
    }
}
