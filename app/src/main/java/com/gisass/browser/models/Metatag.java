
package com.gisass.browser.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metatag {

    @SerializedName("referrer")
    @Expose
    private String referrer;
    @SerializedName("og:image")
    @Expose
    private String ogImage;
    @SerializedName("csrf-param")
    @Expose
    private String csrfParam;
    @SerializedName("csrf-token")
    @Expose
    private String csrfToken;
    @SerializedName("viewport")
    @Expose
    private String viewport;
    @SerializedName("fb:app_id")
    @Expose
    private String fbAppId;
    @SerializedName("og:url")
    @Expose
    private String ogUrl;
    @SerializedName("og:title")
    @Expose
    private String ogTitle;
    @SerializedName("og:description")
    @Expose
    private String ogDescription;
    @SerializedName("og:type")
    @Expose
    private String ogType;
    @SerializedName("og:site_name")
    @Expose
    private String ogSiteName;
    @SerializedName("twitter:card")
    @Expose
    private String twitterCard;
    @SerializedName("twitter:url")
    @Expose
    private String twitterUrl;
    @SerializedName("twitter:title")
    @Expose
    private String twitterTitle;
    @SerializedName("twitter:description")
    @Expose
    private String twitterDescription;
    @SerializedName("twitter:site")
    @Expose
    private String twitterSite;
    @SerializedName("twitter:creator")
    @Expose
    private String twitterCreator;
    @SerializedName("twitter:image:src")
    @Expose
    private String twitterImageSrc;
    @SerializedName("article:published_time")
    @Expose
    private String articlePublishedTime;
    @SerializedName("article:modified_time")
    @Expose
    private String articleModifiedTime;
    @SerializedName("og:image:width")
    @Expose
    private String ogImageWidth;
    @SerializedName("og:image:height")
    @Expose
    private String ogImageHeight;
    @SerializedName("og:locale")
    @Expose
    private String ogLocale;
    @SerializedName("twitter:text:title")
    @Expose
    private String twitterTextTitle;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("revised")
    @Expose
    private String revised;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("msapplication-tilecolor")
    @Expose
    private String msapplicationTilecolor;
    @SerializedName("msapplication-tileimage")
    @Expose
    private String msapplicationTileimage;
    @SerializedName("handheldfriendly")
    @Expose
    private String handheldfriendly;
    @SerializedName("mobileoptimized")
    @Expose
    private String mobileoptimized;
    @SerializedName("apple-mobile-web-app-title")
    @Expose
    private String appleMobileWebAppTitle;
    @SerializedName("application-name")
    @Expose
    private String applicationName;
    @SerializedName("theme-color")
    @Expose
    private String themeColor;
    @SerializedName("og:locale:alternate")
    @Expose
    private String ogLocaleAlternate;
    @SerializedName("fb:admins")
    @Expose
    private String fbAdmins;
    @SerializedName("fb:pages")
    @Expose
    private String fbPages;
    @SerializedName("msapplication-square150x150logo")
    @Expose
    private String msapplicationSquare150x150logo;

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getOgImage() {
        return ogImage;
    }

    public void setOgImage(String ogImage) {
        this.ogImage = ogImage;
    }

    public String getCsrfParam() {
        return csrfParam;
    }

    public void setCsrfParam(String csrfParam) {
        this.csrfParam = csrfParam;
    }

    public String getCsrfToken() {
        return csrfToken;
    }

    public void setCsrfToken(String csrfToken) {
        this.csrfToken = csrfToken;
    }

    public String getViewport() {
        return viewport;
    }

    public void setViewport(String viewport) {
        this.viewport = viewport;
    }

    public String getFbAppId() {
        return fbAppId;
    }

    public void setFbAppId(String fbAppId) {
        this.fbAppId = fbAppId;
    }

    public String getOgUrl() {
        return ogUrl;
    }

    public void setOgUrl(String ogUrl) {
        this.ogUrl = ogUrl;
    }

    public String getOgTitle() {
        return ogTitle;
    }

    public void setOgTitle(String ogTitle) {
        this.ogTitle = ogTitle;
    }

    public String getOgDescription() {
        return ogDescription;
    }

    public void setOgDescription(String ogDescription) {
        this.ogDescription = ogDescription;
    }

    public String getOgType() {
        return ogType;
    }

    public void setOgType(String ogType) {
        this.ogType = ogType;
    }

    public String getOgSiteName() {
        return ogSiteName;
    }

    public void setOgSiteName(String ogSiteName) {
        this.ogSiteName = ogSiteName;
    }

    public String getTwitterCard() {
        return twitterCard;
    }

    public void setTwitterCard(String twitterCard) {
        this.twitterCard = twitterCard;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getTwitterTitle() {
        return twitterTitle;
    }

    public void setTwitterTitle(String twitterTitle) {
        this.twitterTitle = twitterTitle;
    }

    public String getTwitterDescription() {
        return twitterDescription;
    }

    public void setTwitterDescription(String twitterDescription) {
        this.twitterDescription = twitterDescription;
    }

    public String getTwitterSite() {
        return twitterSite;
    }

    public void setTwitterSite(String twitterSite) {
        this.twitterSite = twitterSite;
    }

    public String getTwitterCreator() {
        return twitterCreator;
    }

    public void setTwitterCreator(String twitterCreator) {
        this.twitterCreator = twitterCreator;
    }

    public String getTwitterImageSrc() {
        return twitterImageSrc;
    }

    public void setTwitterImageSrc(String twitterImageSrc) {
        this.twitterImageSrc = twitterImageSrc;
    }

    public String getArticlePublishedTime() {
        return articlePublishedTime;
    }

    public void setArticlePublishedTime(String articlePublishedTime) {
        this.articlePublishedTime = articlePublishedTime;
    }

    public String getArticleModifiedTime() {
        return articleModifiedTime;
    }

    public void setArticleModifiedTime(String articleModifiedTime) {
        this.articleModifiedTime = articleModifiedTime;
    }

    public String getOgImageWidth() {
        return ogImageWidth;
    }

    public void setOgImageWidth(String ogImageWidth) {
        this.ogImageWidth = ogImageWidth;
    }

    public String getOgImageHeight() {
        return ogImageHeight;
    }

    public void setOgImageHeight(String ogImageHeight) {
        this.ogImageHeight = ogImageHeight;
    }

    public String getOgLocale() {
        return ogLocale;
    }

    public void setOgLocale(String ogLocale) {
        this.ogLocale = ogLocale;
    }

    public String getTwitterTextTitle() {
        return twitterTextTitle;
    }

    public void setTwitterTextTitle(String twitterTextTitle) {
        this.twitterTextTitle = twitterTextTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getRevised() {
        return revised;
    }

    public void setRevised(String revised) {
        this.revised = revised;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMsapplicationTilecolor() {
        return msapplicationTilecolor;
    }

    public void setMsapplicationTilecolor(String msapplicationTilecolor) {
        this.msapplicationTilecolor = msapplicationTilecolor;
    }

    public String getMsapplicationTileimage() {
        return msapplicationTileimage;
    }

    public void setMsapplicationTileimage(String msapplicationTileimage) {
        this.msapplicationTileimage = msapplicationTileimage;
    }

    public String getHandheldfriendly() {
        return handheldfriendly;
    }

    public void setHandheldfriendly(String handheldfriendly) {
        this.handheldfriendly = handheldfriendly;
    }

    public String getMobileoptimized() {
        return mobileoptimized;
    }

    public void setMobileoptimized(String mobileoptimized) {
        this.mobileoptimized = mobileoptimized;
    }

    public String getAppleMobileWebAppTitle() {
        return appleMobileWebAppTitle;
    }

    public void setAppleMobileWebAppTitle(String appleMobileWebAppTitle) {
        this.appleMobileWebAppTitle = appleMobileWebAppTitle;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(String themeColor) {
        this.themeColor = themeColor;
    }

    public String getOgLocaleAlternate() {
        return ogLocaleAlternate;
    }

    public void setOgLocaleAlternate(String ogLocaleAlternate) {
        this.ogLocaleAlternate = ogLocaleAlternate;
    }

    public String getFbAdmins() {
        return fbAdmins;
    }

    public void setFbAdmins(String fbAdmins) {
        this.fbAdmins = fbAdmins;
    }

    public String getFbPages() {
        return fbPages;
    }

    public void setFbPages(String fbPages) {
        this.fbPages = fbPages;
    }

    public String getMsapplicationSquare150x150logo() {
        return msapplicationSquare150x150logo;
    }

    public void setMsapplicationSquare150x150logo(String msapplicationSquare150x150logo) {
        this.msapplicationSquare150x150logo = msapplicationSquare150x150logo;
    }

}
