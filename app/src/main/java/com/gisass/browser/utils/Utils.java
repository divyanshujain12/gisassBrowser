package com.gisass.browser.utils;

import android.app.Activity;
import android.view.Menu;

import java.net.URL;

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

    public static boolean isValidUrl(String url) {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }

        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }
}
