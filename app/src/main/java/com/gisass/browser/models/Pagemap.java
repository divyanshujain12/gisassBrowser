
package com.gisass.browser.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pagemap {

    @SerializedName("cse_thumbnail")
    @Expose
    private List<CseThumbnail> cseThumbnail = null;
    @SerializedName("metatags")
    @Expose
    private List<Metatag> metatags = null;
    @SerializedName("cse_image")
    @Expose
    private List<CseImage> cseImage = null;
    @SerializedName("listitem")
    @Expose
    private List<Listitem> listitem = null;

    public List<CseThumbnail> getCseThumbnail() {
        return cseThumbnail;
    }

    public void setCseThumbnail(List<CseThumbnail> cseThumbnail) {
        this.cseThumbnail = cseThumbnail;
    }

    public List<Metatag> getMetatags() {
        return metatags;
    }

    public void setMetatags(List<Metatag> metatags) {
        this.metatags = metatags;
    }

    public List<CseImage> getCseImage() {
        return cseImage;
    }

    public void setCseImage(List<CseImage> cseImage) {
        this.cseImage = cseImage;
    }

    public List<Listitem> getListitem() {
        return listitem;
    }

    public void setListitem(List<Listitem> listitem) {
        this.listitem = listitem;
    }

}
