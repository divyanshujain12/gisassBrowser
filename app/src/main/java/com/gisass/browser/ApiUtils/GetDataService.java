package com.gisass.browser.ApiUtils;

import com.gisass.browser.models.EducationModel;
import com.gisass.browser.models.GoogleSearchModel;
import com.gisass.browser.models.JobModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("education/education_json_api")
    Call<ArrayList<EducationModel>> getEducationCategories();

    @GET("job/job_json_api")
    Call<ArrayList<JobModel>> getJobs();

    @GET("/customsearch/v1")
    Call<GoogleSearchModel> getSearchResult(@Query("key") String key, @Query("cx") String cx, @Query("q") String query);
}