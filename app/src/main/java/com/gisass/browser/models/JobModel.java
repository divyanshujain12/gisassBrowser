
package com.gisass.browser.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobModel {

    @SerializedName("job_id")
    @Expose
    private String jobId;
    @SerializedName("priority_id")
    @Expose
    private int priorityId;
    @SerializedName("job_cat_id")
    @Expose
    private String jobCatId;
    @SerializedName("job_name")
    @Expose
    private String jobName;
    @SerializedName("job_icon")
    @Expose
    private String jobIcon;
    @SerializedName("job_url")
    @Expose
    private String jobUrl;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public int getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(int priorityId) {
        this.priorityId = priorityId;
    }

    public String getJobCatId() {
        return jobCatId;
    }

    public void setJobCatId(String jobCatId) {
        this.jobCatId = jobCatId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobIcon() {
        return jobIcon;
    }

    public void setJobIcon(String jobIcon) {
        this.jobIcon = jobIcon;
    }

    public String getJobUrl() {
        return jobUrl;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

}