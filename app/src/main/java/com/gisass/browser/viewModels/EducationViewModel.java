package com.gisass.browser.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.gisass.browser.adapters.EducationAdapter;
import com.gisass.browser.customViews.CustomDialog;
import com.gisass.browser.globalClass.MyApp;
import com.gisass.browser.models.EducationModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;

public class EducationViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<EducationModel>> educationMutableArray = new MutableLiveData<>();
    private EducationAdapter educationAdapter = null;
    private ArrayList<EducationModel> educationModels = null;
    private CustomDialog customDialog;


    public EducationViewModel(@NonNull Application application) {
        super(application);
    }


    public void init() {
        customDialog = new CustomDialog(((MyApp) getApplication()).getCurrentActivity());
        educationAdapter = new EducationAdapter(this);

    }

    public ArrayList<EducationModel> getEducationModels() {
        educationModels = educationMutableArray.getValue();
        if (educationModels == null)
            educationModels = new ArrayList<>();


        return educationModels;
    }

    public MutableLiveData<ArrayList<EducationModel>> getEducationsFromApi() {

        customDialog.show();
        ((MyApp) getApplication()).getGetDataService().getEducationCategories().enqueue(new Callback<ArrayList<EducationModel>>() {
            @Override
            public void onResponse(Call<ArrayList<EducationModel>> call, Response<ArrayList<EducationModel>> response) {

                educationModels = response.body();
                Collections.sort(educationModels,
                        new Comparator<EducationModel>() {
                            @Override
                            public int compare(EducationModel o1, EducationModel o2) {
                                return o1.getPriorityId().compareTo(o2.getPriorityId());
                            }
                        });
                educationMutableArray.postValue(educationModels);
                educationAdapter.notifyDataSetChanged();
                customDialog.hide();
            }

            @Override
            public void onFailure(Call<ArrayList<EducationModel>> call, Throwable t) {

            }
        });

        return educationMutableArray;
    }

    public void setUrl(Action1<String> url) {
        educationAdapter.setUrl(url);
    }

    public EducationAdapter getEducationAdapter() {
        return educationAdapter;
    }


}
