package io.github.dmitrikudrenko.core.remote.model;

import com.google.gson.annotations.SerializedName;

public class Photo {
    @SerializedName("id")
    private long id;
    @SerializedName("url")
    private String url;

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
