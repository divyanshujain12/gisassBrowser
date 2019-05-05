package com.gisass.browser.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.gisass.browser.R;
import com.gisass.browser.adapters.NewsAdapter;
import com.gisass.browser.models.NewsModel;

import java.util.ArrayList;

public class NewsViewModel extends AndroidViewModel {

    private ArrayList<NewsModel> newsModels;
    private NewsAdapter newsAdapter;

    public NewsViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        newsModels = new ArrayList<>();
        newsAdapter = new NewsAdapter(this);

        for (int i = 0; i < 5; i++) {
            NewsModel newsModel = new NewsModel();
            newsModel.setNewsIcon(R.drawable.hotvideos);
            newsModel.setNews(getApplication().getString(R.string.lorem_ipsum));
            newsModel.setTime("03-May-2019");
            newsModels.add(newsModel);
        }
    }

    public ArrayList<NewsModel> getNewsModels() {
        return newsModels;
    }

    public NewsModel getNewsModel(int pos) {
        return newsModels.get(pos);
    }

    public NewsAdapter getNewsAdapter() {
        return newsAdapter;
    }
}
