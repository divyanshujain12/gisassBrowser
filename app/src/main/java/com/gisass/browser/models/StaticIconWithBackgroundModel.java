package com.gisass.browser.models;

import androidx.databinding.BaseObservable;

public class StaticIconWithBackgroundModel extends BaseObservable {
    private int iconResourceId;
    private String iconName;


    public int getIconResourceId() {
        return iconResourceId;
    }

    public void setIconResourceId(int iconResourceId) {
        this.iconResourceId = iconResourceId;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
