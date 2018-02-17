package io.github.dmitrikudrenko.core.remote.model.woman;

import com.google.gson.annotations.SerializedName;

public class Woman {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("profession")
    private String profession;
    @SerializedName("hair_color")
    private String hairColor;
    @SerializedName("photo")
    private String photo;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("placeholder")
    private String placeholder;
    @SerializedName("photo_count")
    private int photoCount;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfession() {
        return profession;
    }

    public String getHairColor() {
        return hairColor;
    }

    public String getPhoto() {
        return photo;
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getPlaceholder() {
        return placeholder;
    }
}
