package com.gisass.browser.ApiUtils;

import com.gisass.browser.models.EducationModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/education/education_json_api")
    Call<ArrayList<EducationModel>> getEducationCategories();
}