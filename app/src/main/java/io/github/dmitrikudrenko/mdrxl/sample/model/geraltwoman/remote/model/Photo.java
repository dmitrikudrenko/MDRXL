package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.remote.model;

import com.google.gson.annotations.SerializedName;

public class Photo {
    @SerializedName("id")
    private long id;
    @SerializedName("url")
    private String url;
    @SerializedName("woman_id")
    private long womanId;

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public long getWomanId() {
        return womanId;
    }
}
