package io.github.dmitrikudrenko.core.remote.model.video;

import com.google.gson.annotations.SerializedName;

public class Video {
    @SerializedName("id")
    private long id;
    @SerializedName("url")
    private String url;
    @SerializedName("name")
    private String name;
    @SerializedName("duration")
    private long duration;
    @SerializedName("thumbnail")
    private String thumbnail;

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public long getDuration() {
        return duration;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
