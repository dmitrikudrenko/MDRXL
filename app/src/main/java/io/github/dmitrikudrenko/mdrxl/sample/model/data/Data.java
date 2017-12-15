package io.github.dmitrikudrenko.mdrxl.sample.model.data;

import android.support.annotation.StringDef;

import static io.github.dmitrikudrenko.mdrxl.sample.model.data.Data.Fields.*;

public final class Data {
    @StringDef({NAME, FIRST_ATTRIBUTE, SECOND_ATTRIBUTE, THIRD_ATTRIBUTE})
    public @interface Fields {
        String NAME = "NAME";
        String FIRST_ATTRIBUTE = "FIRST_ATTRIBUTE";
        String SECOND_ATTRIBUTE = "SECOND_ATTRIBUTE";
        String THIRD_ATTRIBUTE = "THIRD_ATTRIBUTE";
    }

    private final int id;
    private String name;
    private String firstAttribute;
    private String secondAttribute;
    private String thirdAttribute;

    public Data(final int id, final String name, final String firstAttribute,
                final String secondAttribute, final String thirdAttribute) {
        this.id = id;
        this.name = name;
        this.firstAttribute = firstAttribute;
        this.secondAttribute = secondAttribute;
        this.thirdAttribute = thirdAttribute;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getFirstAttribute() {
        return firstAttribute;
    }

    public void setFirstAttribute(final String firstAttribute) {
        this.firstAttribute = firstAttribute;
    }

    public String getSecondAttribute() {
        return secondAttribute;
    }

    public void setSecondAttribute(final String secondAttribute) {
        this.secondAttribute = secondAttribute;
    }

    public String getThirdAttribute() {
        return thirdAttribute;
    }

    public void setThirdAttribute(final String thirdAttribute) {
        this.thirdAttribute = thirdAttribute;
    }

    public void set(@Fields final String field, final Object value) {
        switch (field) {
            case NAME:
                name = (String) value;
                break;
            case FIRST_ATTRIBUTE:
                firstAttribute = (String) value;
                break;
            case SECOND_ATTRIBUTE:
                secondAttribute = (String) value;
                break;
            case THIRD_ATTRIBUTE:
                thirdAttribute = (String) value;
                break;
        }
    }

    public Data copy() {
        return new Data(
                id,
                name,
                firstAttribute,
                secondAttribute,
                thirdAttribute
        );
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", firstAttribute='" + firstAttribute + '\'' +
                ", secondAttribute='" + secondAttribute + '\'' +
                ", thirdAttribute='" + thirdAttribute + '\'' +
                '}';
    }
}
