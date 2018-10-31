package br.com.stanzione.uoltest.data;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;

public class News extends RealmObject {

    @SerializedName("type")
    private String type;
    @SerializedName("title")
    private String title;
    @SerializedName("thumb")
    private String thumbUrl;
    @SerializedName("updated")
    private Date updatedDate;
    @SerializedName("share-url")
    private String shareUrl;
    @SerializedName("webview-url")
    private String webviewUrl;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getWebviewUrl() {
        return webviewUrl;
    }

    public void setWebviewUrl(String webviewUrl) {
        this.webviewUrl = webviewUrl;
    }
}
