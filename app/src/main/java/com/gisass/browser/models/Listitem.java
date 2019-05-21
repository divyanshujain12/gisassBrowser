
package com.gisass.browser.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Listitem {

    @SerializedName("item")
    @Expose
    private String item;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("position")
    @Expose
    private String position;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}
