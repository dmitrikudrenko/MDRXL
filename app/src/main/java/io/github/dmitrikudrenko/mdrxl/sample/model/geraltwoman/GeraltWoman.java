package io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman;

import android.support.annotation.StringDef;

import static io.github.dmitrikudrenko.mdrxl.sample.model.geraltwoman.GeraltWoman.Fields.*;

public final class GeraltWoman {
    @StringDef({NAME, PHOTO, PROFESSION, HAIR_COLOR})
    public @interface Fields {
        String NAME = "NAME";
        String PHOTO = "PHOTO";
        String PROFESSION = "PROFESSION";
        String HAIR_COLOR = "HAIR_COLOR";
    }

    private final long id;
    private String name;
    private String photo;
    private String profession;
    private String hairColor;

    public GeraltWoman(final long id, final String name, final String photo,
                       final String profession, final String hairColor) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.profession = profession;
        this.hairColor = hairColor;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(final String photo) {
        this.photo = photo;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(final String profession) {
        this.profession = profession;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(final String hairColor) {
        this.hairColor = hairColor;
    }

    public void set(@Fields final String field, final Object value) {
        switch (field) {
            case NAME:
                name = (String) value;
                break;
            case PHOTO:
                photo = (String) value;
                break;
            case PROFESSION:
                profession = (String) value;
                break;
            case HAIR_COLOR:
                hairColor = (String) value;
                break;
        }
    }

    @Override
    public String toString() {
        return "GeraltWoman{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", profession='" + profession + '\'' +
                ", hairColor='" + hairColor + '\'' +
                '}';
    }
}
