package io.github.dmitrikudrenko.mdrxl.sample.model.data;

public final class Data {
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
