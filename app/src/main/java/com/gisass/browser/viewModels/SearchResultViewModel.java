package com.gisass.browser.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.gisass.browser.ApiUtils.GetDataService;
import com.gisass.browser.ApiUtils.RetrofitClientInstance;
import com.gisass.browser.adapters.SearchResultAdapter;
import com.gisass.browser.models.GoogleSearchModel;
import com.gisass.browser.models.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchResultViewModel extends AndroidViewModel {


    private GoogleSearchModel googleSearchModel = new GoogleSearchModel();


    private SearchResultAdapter searchResultAdapter;
    private GetDataService getDataService;
    private static final String GOOGLE_KEY = "AIzaSyD_8FjOCDArg8u8Nx9HdvactSL50z-8X-s";
    private static final String GOOGLE_CX = "005503866809826999211:eqkqu0bguzo";
    private String search_key = "";

    public SearchResultViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        searchResultAdapter = new SearchResultAdapter(this);
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance().newBuilder().baseUrl("https://www.googleapis.com").addConverterFactory(GsonConverterFactory.create()).build();
        getDataService = retrofit.create(GetDataService.class);
    }


    public List<Item> getGoogleSearchModel() {
        return googleSearchModel.getItems();
    }

    public void getSearchResultFromApi(String searchQuery) {

        getDataService.getSearchResult(GOOGLE_KEY, GOOGLE_CX, searchQuery).enqueue(new Callback<GoogleSearchModel>() {
            @Override
            public void onResponse(Call<GoogleSearchModel> call, Response<GoogleSearchModel> response) {
                googleSearchModel = response.body();
                searchResultAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GoogleSearchModel> call, Throwable t) {

            }
        });
    }

    public SearchResultAdapter getSearchResultAdapter() {
        return searchResultAdapter;
    }

    public String getSearch_key() {
        return search_key;
    }

    public void setSearch_key(String search_key) {
        this.search_key = search_key;
    }
}
