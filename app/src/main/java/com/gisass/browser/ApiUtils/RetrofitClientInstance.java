package com.gisass.browser.ApiUtils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    public static final String BASE_URL = "http://peeran.com/gisass/";


    static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .cache(null)
            .build();

    public static Retrofit getRetrofitInstance(String baseUrl) {
        if (retrofit == null || !retrofit.baseUrl().url().toString().equals(baseUrl)) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}