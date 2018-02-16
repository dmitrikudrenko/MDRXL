package io.github.dmitrikudrenko.core.remote.model.woman.photo;

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
