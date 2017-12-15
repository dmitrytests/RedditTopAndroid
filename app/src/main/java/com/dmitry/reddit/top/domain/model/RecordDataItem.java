package com.dmitry.reddit.top.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dima on 12/5/17.
 */

public class RecordDataItem {
    @SerializedName("thumbnail_width")
    private int thumbWidth;
    @SerializedName("title")
    private String title;
    @SerializedName("thumbnail")
    private String thumbUrl;
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;
    @SerializedName("created_utc")
    private long createdTime;
    @SerializedName("author")
    private String author;
    @SerializedName("num_comments")
    private int comments;
    @SerializedName("is_video")
    private boolean isVideo;
    @SerializedName("domain")
    private String domain;
    @SerializedName("preview")
    Preview preview;

    public String getSourceUrl(){
        if(preview!=null
                && preview.images!=null
                && !preview.images.isEmpty()
                && preview.images.get(0)!=null
                && preview.images.get(0).source!=null
                ){
            return preview.images.get(0).source.url;
        }
        return null;
    }

    public int getComments() {
        return comments;
    }

    public int getThumbWidth() {
        return thumbWidth;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public String getDomain() {
        return domain;
    }

    public String getFormattedTimeInterval() {
        long d = System.currentTimeMillis()/1000 - createdTime;
        int hours = (int) (d / 3600);
        int mins = (int) ((d / 60) % 60);
        StringBuilder b = new StringBuilder();
        if (hours > 0) {
            b.append(hours).append("h ");
        }else if (mins > 0 ) {
            b.append(mins).append("min");
        } else{
            return "1min";
        }
        return b.toString();
    }

    private static class Preview{
        List<ImagePreview> images;
    }

    private static class ImagePreview{
        @SerializedName("source")
        ImageSource source;
        @SerializedName("id")
        String id;
    }

    private static class ImageSource{
        @SerializedName("url")
        String url;
        @SerializedName("width")
        int width;
        @SerializedName("height")
        int height;
    }
}
