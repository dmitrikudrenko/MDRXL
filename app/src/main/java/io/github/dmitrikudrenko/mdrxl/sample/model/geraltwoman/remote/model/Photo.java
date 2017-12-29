package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.remote.model;

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
