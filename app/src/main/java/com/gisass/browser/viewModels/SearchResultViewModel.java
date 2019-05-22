package com.gisass.browser.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gisass.browser.ApiUtils.GetDataService;
import com.gisass.browser.ApiUtils.RetrofitClientInstance;
import com.gisass.browser.adapters.SearchResultAdapter;
import com.gisass.browser.customViews.CustomDialog;
import com.gisass.browser.globalClass.MyApp;
import com.gisass.browser.models.GoogleSearchModel;
import com.gisass.browser.models.Item;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.functions.Action1;

public class SearchResultViewModel extends AndroidViewModel {


    private GoogleSearchModel googleSearchModel = new GoogleSearchModel();
    private List<Item> googleItems = new ArrayList<>();
    private RecyclerView recyclerView;
    private SearchResultAdapter searchResultAdapter;
    private GetDataService getDataService;
    private static final String GOOGLE_KEY = "AIzaSyD_8FjOCDArg8u8Nx9HdvactSL50z-8X-s";
    private static final String GOOGLE_CX = "005503866809826999211:eqkqu0bguzo";
    private String search_key = "";
    private CustomDialog customDialog;

    public SearchResultViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        searchResultAdapter = new SearchResultAdapter(this);
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance("https://www.googleapis.com");
        getDataService = retrofit.create(GetDataService.class);
        customDialog = new CustomDialog(((MyApp) getApplication()).getCurrentActivity());

        setAdapterToRecyclerView();
    }


    public List<Item> getGoogleSearchModel() {
        return googleItems;
    }

    public void getSearchResultFromApi(String searchQuery) {
        googleItems = new ArrayList<>();
        customDialog.show();

        getDataService.getSearchResult(GOOGLE_KEY, GOOGLE_CX, searchQuery).enqueue(new Callback<GoogleSearchModel>() {
            @Override
            public void onResponse(@NotNull Call<GoogleSearchModel> call, @NotNull Response<GoogleSearchModel> response) {
                customDialog.hide();
                googleSearchModel = response.body();
                googleItems.addAll(googleSearchModel.getItems());
                recyclerView.setAdapter(searchResultAdapter);
            }

            @Override
            public void onFailure(Call<GoogleSearchModel> call, Throwable t) {
                customDialog.hide();
            }
        });
    }

    public String getSearch_key() {
        return search_key;
    }

    public void setSearch_key(String search_key) {
        this.search_key = search_key;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    private void setAdapterToRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(((MyApp) getApplication()).getCurrentActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(searchResultAdapter);
    }

    public void setUrl(Action1<String> url) {
        searchResultAdapter.setUrl(url);
    }
}


