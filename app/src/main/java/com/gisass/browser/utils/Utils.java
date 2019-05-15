package com.gisass.browser.utils;

import android.app.Activity;
import android.view.Menu;

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

    public void showToolbarItems(Menu menu, boolean show, Activity activity) {
        activity.invalidateOptionsMenu();
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(show);
        }

    }
}
