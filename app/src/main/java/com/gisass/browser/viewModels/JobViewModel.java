package com.gisass.browser.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.gisass.browser.adapters.JobAdapter;
import com.gisass.browser.globalClass.MyApp;
import com.gisass.browser.models.JobModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.functions.Action1;

public class JobViewModel extends AndroidViewModel {
    public JobViewModel(@NonNull Application application) {
        super(application);
    }


    private JobAdapter jobAdapter = null;
    ArrayList<JobModel> jobModels = null;


    public void init() {
        jobAdapter = new JobAdapter(this);

    }

    public ArrayList<JobModel> getJobModels() {
        if (jobModels == null)
            jobModels = new ArrayList<>();
        return jobModels;
    }

    public void getJobsFromApi() {
        ((MyApp) getApplication()).getGetDataService().getJobs().enqueue(new Callback<ArrayList<JobModel>>() {
            @Override
            public void onResponse(Call<ArrayList<JobModel>> call, Response<ArrayList<JobModel>> response) {
                jobModels = response.body();
                Collections.sort(jobModels,
                        new Comparator<JobModel>() {
                            @Override
                            public int compare(JobModel o1, JobModel o2) {
                                return Integer.compare(o1.getPriorityId(), o2.getPriorityId());
                            }
                        });

                jobAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<JobModel>> call, Throwable t) {

            }
        });

    }

    public void setUrl(Action1<String> url) {
        jobAdapter.setUrl(url);
    }

    public JobAdapter getJobAdapter() {
        return jobAdapter;
    }

}


