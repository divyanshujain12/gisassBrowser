package com.gisass.browser.models;


import androidx.databinding.BaseObservable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EducationModel extends BaseObservable {

    @SerializedName("education_id")
    @Expose
    private String educationId;
    @SerializedName("edutype_id")
    @Expose
    private String edutypeId;
    @SerializedName("priority_id")
    @Expose
    private String priorityId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("education_icon")
    @Expose
    private String educationIcon;
    @SerializedName("url")
    @Expose
    private String url;

    public String getEducationId() {
        return educationId;
    }

    public void setEducationId(String educationId) {
        this.educationId = educationId;
    }

    public String getEdutypeId() {
        return edutypeId;
    }

    public void setEdutypeId(String edutypeId) {
        this.edutypeId = edutypeId;
    }

    public String getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(String priorityId) {
        this.priorityId = priorityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEducationIcon() {
        return educationIcon;
    }

    public void setEducationIcon(String educationIcon) {
        this.educationIcon = educationIcon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}