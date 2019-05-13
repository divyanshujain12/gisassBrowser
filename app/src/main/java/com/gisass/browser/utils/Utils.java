package com.gisass.browser.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class Utils {
    private static final Utils ourInstance = new Utils();

    public static Utils getInstance() {
        return ourInstance;
    }

    private Utils() {
    }

    public String getFormattedUrlString(String url) {

        if (!url.startsWith("www.") && !url.startsWith("http://")) {
            url = "www." + url;
        }
        if (!url.startsWith("http://")) {
            url = "http://" + url;
        }
        return url;
    }


}
